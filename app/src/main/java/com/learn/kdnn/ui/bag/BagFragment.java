package com.learn.kdnn.ui.bag;


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
import com.learn.kdnn.model.Product;
import com.learn.kdnn.ui.checkout.CheckoutFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BagFragment extends Fragment {

    private FragmentBagBinding binding;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        RecyclerView recyclerView  = binding.rcvBag;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getBag().observe(requireActivity(), bagMap -> {
            if (bagMap != null && !bagMap.isEmpty()) {
                List<Product> products = new ArrayList<>();
                Set<Integer> keys = bagMap.keySet();
                for (Integer key :
                        keys) {
                    products.add((Product) bagMap.get(key));
                }
                BagItemViewAdapter adapter = new BagItemViewAdapter(products,getContext());
                recyclerView.setAdapter(adapter);
            }
        });

        binding.btnReturnHome.setOnClickListener(v->((MainActivity)getContext()).getMainNavController().navigate(R.id.action_nav_bag_to_nav_home));
        binding.btnCheckout.setOnClickListener(v->{
            FragmentManager mana = ((MainActivity) getContext()).getSupportFragmentManager();
            new CheckoutFragment().show(mana,"check out fragment");
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}