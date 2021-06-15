package com.learn.kdnn.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentCheckoutMethodBinding;

public class CheckoutMethodFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private FragmentCheckoutMethodBinding binding;

    public CheckoutMethodFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutMethodBinding.inflate(inflater, container, false);

        binding.btnCloseCheckoutMthodFragment.setOnClickListener(this);
        binding.btnReturnCheckout.setOnClickListener(this);
        binding.codMethod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
                new ShippingDetailFragment().show(manager, "input shipping detail fragment");
                this.dismiss();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCloseCheckoutMthodFragment: {
                this.dismiss();
                break;
            }
            case R.id.btnReturnCheckout: {
                FragmentManager manager = ((MainActivity) getContext()).getSupportFragmentManager();
                new CheckoutFragment().show(manager, "checkout fragment");
                this.dismiss();
                break;
            }
        }
    }
}