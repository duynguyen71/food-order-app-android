package com.learn.kdnn.ui.bag;


import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentBagBinding;
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Product;
import com.learn.kdnn.ui.checkout.CheckoutFragment;
import com.learn.kdnn.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BagFragment extends Fragment implements BagItemViewAdapter.OnOptionsClickListener {

    private FragmentBagBinding binding;
    private MainViewModel viewModel;

    private double totalSalesPrice;
    private double totalStandardPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (viewModel.getBag().getValue() == null || viewModel.getBag().getValue().isEmpty()) {
            binding.checkoutContainer.setVisibility(View.GONE);
        }


        RecyclerView recyclerView = binding.rcvBag;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel.getBag().observe(requireActivity(), bagMap -> {

            if(bagMap!=null){

                ((MainActivity) getContext()).updateBagCounter();
                if(bagMap.size()<=0){

                    binding.checkoutContainer.setVisibility(View.GONE);
                }else{
                    binding.checkoutContainer.setVisibility(View.VISIBLE);

                }

                binding.productInBag.setText(String.valueOf(bagMap.size()));
                this.totalSalesPrice = AppUtils.getTotalSalesPrice(bagMap);
                binding.bagTotal.setText("$" + String.format("%.2f", totalSalesPrice));

                boolean isDiscount = false;
                List<CartItem> cart = new ArrayList<>();
                Set<Long> keys = bagMap.keySet();
                for (Long key :
                        keys) {
                    CartItem item = (CartItem) bagMap.get(key);
                    if (item.getProduct().getDiscountPer() > 0) {
                        isDiscount = true;
                    }
                    cart.add(item);
                }
                if (isDiscount) {
                    binding.tvTotalStandardPrice.setVisibility(View.VISIBLE);
                    this.totalStandardPrice = AppUtils.getDefailtPrice(bagMap);
                    binding.tvTotalStandardPrice.setText("$" + String.format("%.2f", this.totalStandardPrice));
                    binding.tvTotalStandardPrice.setPaintFlags(binding.tvTotalStandardPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }


                BagItemViewAdapter adapter = new BagItemViewAdapter(cart, getContext());
                adapter.setOnOptionsClickListener(this);
                recyclerView.setAdapter(adapter);
            }


        });

        binding.btnReturnHome.setOnClickListener(v -> ((MainActivity) getContext()).getMainNavController().navigate(R.id.action_nav_bag_to_nav_home));
        binding.btnCheckout.setOnClickListener(v -> {
            FragmentManager mana = ((MainActivity) getContext()).getSupportFragmentManager();
            CheckoutFragment.newInstance(viewModel.getShippingMethod().getValue()).show(mana, "check out fragment");
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onOptionClick(int index, Product product) {
        FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
        new BagItemOptionsFragment(product.getId()).show(manager, "bag item options fragment");
    }
}