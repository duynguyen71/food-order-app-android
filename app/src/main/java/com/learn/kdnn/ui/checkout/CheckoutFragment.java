package com.learn.kdnn.ui.checkout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.MainViewModel;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentCheckoutBinding;
import com.learn.kdnn.model.CartItem;
import com.learn.kdnn.model.Order;
import com.learn.kdnn.model.ShippingAddress;
import com.learn.kdnn.utils.AppUtils;
import com.learn.kdnn.utils.ApplicationUiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CheckoutFragment extends BottomSheetDialogFragment {

    public static final String ARG_SHIPPING_METHOD = "ARG_SHIPPING_METHOD";
    private FragmentCheckoutBinding binding;
    private final String TAG = getClass().getSimpleName();
    private MainViewModel viewModel;

    private Integer method;

    private double totalSalePrice;
    private double totalStandardPrice;

    public static CheckoutFragment newInstance(int method) {

        Bundle args = new Bundle();
        args.putInt("ARG_SHIPPING_METHOD", method);
        CheckoutFragment fragment = new CheckoutFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (getArguments() != null) {
            method = getArguments().getInt(ARG_SHIPPING_METHOD);
        }
        this.setCancelable(false);

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);

        MutableLiveData<HashMap<Long, Object>> viewModelBag = viewModel.getBag();
        HashMap<Long, Object> bag = viewModelBag.getValue();
        if (bag != null && !bag.isEmpty()) {
            totalSalePrice = AppUtils.getTotalSalesPrice(bag);
            binding.checkoutSalesPrice.setText("$" + totalSalePrice);
            totalStandardPrice = AppUtils.getDefailtPrice(bag);
            binding.checkoutStandardPrice.setText("$" + totalStandardPrice);
        }

        if (getArguments() != null) {
            if (method == 1) {
                binding.selectShippingMethod.setText("COD");
            }

        }
        MainActivity mainActivity = (MainActivity) getContext();

        binding.btnCloseCheckout.setOnClickListener(v -> this.dismiss());
        binding.selectShippingMethod.setOnClickListener(v -> {
            FragmentManager manager = mainActivity.getSupportFragmentManager();
            new CheckoutMethodFragment().show(manager, "check out method");
            this.dismiss();
        });

        inFlartListOfImage();


        binding.btnPlaceOrder.setOnClickListener(v -> handleOrderProcess());

        return binding.getRoot();
    }


    private void handleOrderProcess() {
        MutableLiveData<ShippingAddress> shippingDetail = viewModel.getShippingAdress();
        ShippingAddress shippingDetailValue = shippingDetail.getValue();
        if (shippingDetailValue == null || shippingDetailValue.getAddress() == null || shippingDetailValue.getPhone() == null || shippingDetailValue.getUsername() == null) {
//            ApplicationUiUtils.showCustomToast(getContext(), Toast.LENGTH_LONG, "Please provide all info about shipping detail", getLayoutInflater());
            ApplicationUiUtils.showCustomToast(getContext(), Toast.LENGTH_LONG, "Please select shipping method to place order", getLayoutInflater());
            return;
        }
        MutableLiveData<HashMap<Long, Object>> allItemInBag = viewModel.getBag();
        Map<Long, Object> o = allItemInBag.getValue();
        if (allItemInBag != null && allItemInBag.getValue() != null) {
            String uid = FirebaseAuth.getInstance().getUid();
            Order order = new Order();
            order.setUserId(uid);
            order.setOrderId(UUID.randomUUID().toString());
            order.setOrderDate(new Date());
            order.setTotalPrice(this.totalSalePrice);
            order.setTotalStandardPrice(this.totalStandardPrice);

            List<CartItem> products = new ArrayList<>();

            for (Map.Entry<Long, Object> entry : o.entrySet()) {
                CartItem value = (CartItem) entry.getValue();
                products.add(value);
            }
            order.setCartItems(products);

            order.setShippingAddress(shippingDetailValue);

            MainActivity mainActivity = ((MainActivity) getContext());
            FirebaseDatabase
                    .getInstance()
                    .getReference("orders")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child(order.getOrderId())
                    .setValue(order)
                    .addOnSuccessListener(success -> {
                        allItemInBag.setValue(new HashMap<>());
                        ApplicationUiUtils.showCustomToast(getContext(), Toast.LENGTH_SHORT, "Order Successful", getLayoutInflater());
                        this.dismiss();
                        mainActivity.getMainNavController()
                                .navigate(R.id.action_nav_bag_to_nav_home);

                    });
        }

    }

    private void inFlartListOfImage() {

        ViewGroup vg = binding.checkoutImgGp;
        HashMap<Long, Object> map = viewModel.getBag().getValue();
        Set<Long> keys = map.keySet();
        for (Long key :
                keys) {
            CartItem cartItem = (CartItem) map.get(key);
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.checkout_img_entry, vg, false);
            Glide.with(getContext()).load(cartItem.getProduct().getPrimaryImgUrl())
                    .centerCrop()
                    .into(imageView);
            vg.addView(imageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
