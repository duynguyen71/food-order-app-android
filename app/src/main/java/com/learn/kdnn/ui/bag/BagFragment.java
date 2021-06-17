package com.learn.kdnn.ui.bag;


import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BagFragment extends Fragment implements BagItemViewAdapter.OnOptionsClickListener {

    private FragmentBagBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (viewModel.getBag().getValue().isEmpty()) {
            binding.checkoutContainer.setVisibility(View.GONE);
        }


        RecyclerView recyclerView = binding.rcvBag;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel.getBag().observe(requireActivity(), bagMap -> {
            if (bagMap != null) {

                binding.checkoutContainer.setVisibility(View.VISIBLE);

                binding.productInBag.setText(String.valueOf(bagMap.size()));
                binding.bagTotal.setText("$"+String.format("%.2f",getTotalPrice(bagMap)));
                List<CartItem> cart = new ArrayList<>();
                Set<Integer> keys = bagMap.keySet();
                for (Integer key :
                        keys) {
                    CartItem item = (CartItem) bagMap.get(key);
                    cart.add(item);
                }
                BagItemViewAdapter adapter = new BagItemViewAdapter(cart, getContext());
                adapter.setOnOptionsClickListener(this);
                recyclerView.setAdapter(adapter);
                if(bagMap.size()==0){
                    binding.checkoutContainer.setVisibility(View.GONE);

                }
            }else{
                binding.checkoutContainer.setVisibility(View.GONE);
            }
        });

        binding.btnReturnHome.setOnClickListener(v -> ((MainActivity) getContext()).getMainNavController().navigate(R.id.action_nav_bag_to_nav_home));
        binding.btnCheckout.setOnClickListener(v -> {
            FragmentManager mana = ((MainActivity) getContext()).getSupportFragmentManager();
            new CheckoutFragment().show(mana, "check out fragment");
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public double getTotalPrice(Map<Integer, Object> bag) {
        double rs = 0;
        Set<Integer> keys = bag.keySet();
        for (Integer key :
                keys) {
            CartItem item = (CartItem)bag.get(key);
            int quality = item.getQuality();
            double standardPrice = item.getProduct().getPrice();
            double per = item.getProduct().getDiscountPer();
            Log.d("hih", "getTotalPrice: "+(double)per/100);
            rs+= quality*(standardPrice - (standardPrice * per / 100));

        }
        return rs;
    }

    @Override
    public void onOptionClick(int index, Product product) {
        FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
        new BagItemOptionsFragment(product.getId()).show(manager, "bag item options fragment");
    }
}