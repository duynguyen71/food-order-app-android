package com.learn.kdnn.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentShippingDetailBinding;

public class ShippingDetailFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private FragmentShippingDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShippingDetailBinding.inflate(inflater, container, false);
        binding.btnReturnCheckoutMethodd.setOnClickListener(this);
        binding.btnCloseShippingAdressFragment.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReturnCheckoutMethodd: {
                FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
                new CheckoutMethodFragment().show(manager, "checkout method fragment");
                this.dismiss();
                break;
            }
            case R.id.btnCloseShippingAdressFragment: {
                this.dismiss();
                break;
            }
        }
    }
}