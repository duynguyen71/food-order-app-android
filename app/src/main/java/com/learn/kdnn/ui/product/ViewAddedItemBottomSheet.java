package com.learn.kdnn.ui.product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentViewAddedItemBinding;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewAddedItemBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    public static String ADDED_ITEM_ID = "addItemIndex";
    public static String QUALITY = "quality";
    private FragmentViewAddedItemBinding binding;
    private long id;
    private int quality;
    private Product addedProduct;

    public static ViewAddedItemBottomSheet newInstance(long id, int quality) {
        ViewAddedItemBottomSheet fragment = new ViewAddedItemBottomSheet();
        Bundle args = new Bundle();
        args.putLong(ADDED_ITEM_ID, id);
        args.putInt(QUALITY, quality);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        if (getArguments() != null) {
            this.id = getArguments().getLong(ADDED_ITEM_ID);
            this.quality = getArguments().getInt(QUALITY);
        }
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        List<Product> products = mainViewModel.getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId() == id) {
                addedProduct = p;
                break;
            }

        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = FragmentViewAddedItemBinding.inflate(inflater, container, false);

        binding.btnClose.setOnClickListener(v -> this.dismiss());

        binding.btnViewBag.setOnClickListener(this);


        binding.addedProductName.setText(addedProduct.getName());
        binding.addedQuality.setText(String.valueOf(quality));
        Glide.with(requireContext())
                .load(addedProduct.getPrimaryImgUrl())
                .centerCrop()
                .into(binding.addedImg);
        return binding.getRoot();
    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        this.dismiss();
        MainActivity mainActivity = (MainActivity) getContext();
        switch (v.getId()) {
            case R.id.btnPlaceOrder: {
                assert mainActivity != null;
                mainActivity.getMainNavController().navigate(R.id.action_nav_product_details_to_bagFragment);
                break;
            }
            case R.id.btnViewBag: {
                assert mainActivity != null;
                mainActivity.getMainNavController()
                        .navigate(R.id.action_nav_product_details_to_bagFragment);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
