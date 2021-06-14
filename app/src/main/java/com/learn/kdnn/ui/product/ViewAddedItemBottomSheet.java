package com.learn.kdnn.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.AppCacheManage;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.ViewAddedItemBottomFragmentBinding;
import com.learn.kdnn.model.Product;

import org.jetbrains.annotations.NotNull;

public class ViewAddedItemBottomSheet extends BottomSheetDialogFragment {

    public static String ADDED_ITEM_INDEX = "addItemIndex";
    private ViewAddedItemBottomFragmentBinding binding;
    private int index;

    public static ViewAddedItemBottomSheet newInstance(int index) {
        ViewAddedItemBottomSheet fragment = new ViewAddedItemBottomSheet();
        Bundle args = new Bundle();
        args.putInt(ADDED_ITEM_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.index = getArguments().getInt(ADDED_ITEM_INDEX);
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        binding = ViewAddedItemBottomFragmentBinding.inflate(inflater, container, false);

        binding.btnClose.setOnClickListener(v -> {
            this.dismiss();
        });

        binding.btnViewBag.setOnClickListener(v -> {
            this.dismiss();
            MainActivity mainActivity = (MainActivity) getContext();
            mainActivity.getMainNavController().navigate(R.id.action_nav_product_details_to_bagFragment);
        });

        Product addedProduct = AppCacheManage.products.get(this.index);
        binding.addedProductName.setText(addedProduct.getName());
        Glide.with(getContext())
                .load(addedProduct.getImgUri())
                .centerCrop()
                .into(binding.addedImg);


        return binding.getRoot();
    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
