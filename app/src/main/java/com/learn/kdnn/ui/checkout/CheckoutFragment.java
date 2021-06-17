package com.learn.kdnn.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentCheckoutBinding;
import com.learn.kdnn.model.CartItem;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public class CheckoutFragment extends BottomSheetDialogFragment {

    private FragmentCheckoutBinding binding;
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        binding.btnCloseCheckout.setOnClickListener(v -> {
            this.dismiss();
        });
        binding.selectShippingMethod.setOnClickListener(v -> {
            FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
            new CheckoutMethodFragment().show(manager, "check out method");
            this.dismiss();

        });
        ViewGroup vg = binding.checkoutImgGp;
        HashMap<Integer, Object> map = viewModel.getBag().getValue();
        Set<Integer> keys = map.keySet();
        for (Integer key :
                keys) {
            CartItem cartItem = (CartItem) map.get(key);
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.checkout_img_entry, vg,false);
            Glide.with(getContext()).load(cartItem.getProduct().getPrimaryImgUrl())
                    .centerCrop()
                    .into(imageView);
            vg.addView(imageView);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
