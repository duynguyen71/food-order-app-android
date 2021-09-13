package com.learn.kdnn.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.kdnn.MainActivity;
import com.learn.kdnn.R;
import com.learn.kdnn.databinding.FragmentAccountBinding;
import com.learn.kdnn.model.Order;
import com.learn.kdnn.utils.ApplicationUiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;


    public AccountFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        binding.orderLoader.setVisibility(View.VISIBLE);

        RecyclerView rcv = binding.rcvAccountOrders;
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcv.setHasFixedSize(true);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase
                .getInstance()
                .getReference("orders")
                .child(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        List<Order> orderList = new ArrayList<>();
                        for (DataSnapshot data :
                                task.getResult().getChildren()) {
                            orderList.add(data.getValue(Order.class));

                        }
                        OrderItemAdapter adapter = new OrderItemAdapter(orderList, getContext());
                        adapter.setOnRemoveOrderItem((position, orderId) -> {
                            FirebaseDatabase
                                    .getInstance()
                                    .getReference("orders")
                                    .child(uid)
                                    .child(orderId)
                                    .removeValue()
                                    .addOnSuccessListener(runnable -> {
                                        ApplicationUiUtils.showCustomToast(getContext(), Toast.LENGTH_SHORT, "Remove order success", getLayoutInflater());
                                    });
                        });
                        rcv.setAdapter(adapter);
                        binding.orderLoader.setVisibility(View.GONE);
                        binding.dontHaveOrder.setVisibility(View.GONE);

                    } else {
                        binding.orderLoader.setVisibility(View.GONE);
                        binding.dontHaveOrder.setVisibility(View.VISIBLE);
                    }
                });

        binding.orderNow.setOnClickListener(view -> {
            MainActivity main = (MainActivity) requireActivity();
            main
                    .getMainNavController()
                    .navigate(R.id.account_to_home);
        });
        return binding.getRoot();
    }


}