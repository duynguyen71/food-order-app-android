package com.learn.kdnn.ui.checkout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentShippingDetailBinding;
import com.learn.kdnn.model.ShippingAddress;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ShippingDetailFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private FragmentShippingDetailBinding binding;
    private MainViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding = FragmentShippingDetailBinding.inflate(inflater, container, false);

        viewModel
                .getUser()
                .observe(requireActivity(), userInfo -> {
                    if (userInfo != null) {
                        binding.shippingAddress.setText(userInfo.getAddress());
                        binding.shippingPhone.setText(userInfo.getPhoneNumber());
                        String displayName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
                        binding.shippingName.setText(displayName);
                    }
                });

        binding.btnReturnCheckoutMethodd.setOnClickListener(this);
        binding.btnCloseShippingAdressFragment.setOnClickListener(this);
        binding.btnSubmitShippingDetail.setOnClickListener(this);

        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getContext();
        assert mainActivity != null;
        FragmentManager manager = mainActivity.getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.btnReturnCheckoutMethodd: {
                new CheckoutMethodFragment().show(manager, "checkout method fragment");
                this.dismiss();
                break;
            }
            case R.id.btnCloseShippingAdressFragment: {
                this.dismiss();
                break;
            }
            case R.id.btnSubmitShippingDetail: {
                viewModel.getShippingMethod().setValue(1);

                ShippingAddress shippingAddress = new ShippingAddress();
                Editable phone = binding.shippingPhone.getText();
                if (phone==null|| !Patterns.PHONE.matcher(phone.toString()).matches()) {
                    binding.shippingPhone.setError("Phone is not valid");
                    return;
                }
                shippingAddress.setPhone(phone.toString());

                Editable address = binding.shippingAddress.getText();
                if (address==null||TextUtils.isEmpty(address.toString())) {
                    binding.shippingAddress.setError("Address is not valid");
                    return;

                }
                shippingAddress.setAddress(address.toString());


                Editable name = binding.shippingName.getText();
                if (name==null ||TextUtils.isEmpty(name.toString())) {
                    binding.shippingName.setError("Name is not valid");
                    return;

                }
                shippingAddress.setUsername(name.toString());

                viewModel.getShippingAdress().setValue(shippingAddress);

                CheckoutFragment.newInstance(1).show(manager, "check out fragment");
                this.dismiss();
            }
        }
    }
}