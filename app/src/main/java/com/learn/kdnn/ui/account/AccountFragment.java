package com.learn.kdnn.ui.account;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.kdnn.databinding.FragmentAccountBinding;
import com.learn.kdnn.model.Order;

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            task.getResult().getChildren().forEach(item -> {
                                orderList.add((Order) item.getValue(Order.class));
                            });
                        } else {
                            for (DataSnapshot data :
                                    task.getResult().getChildren()) {
                                orderList.add(data.getValue(Order.class));

                            }
                        }
                        OrderItemAdapter adapter = new OrderItemAdapter(orderList, getContext());
                        rcv.setAdapter(adapter);
                        binding.orderLoader.setVisibility(View.GONE);
                    } else {
                        binding.orderLoader.setVisibility(View.GONE);
                        binding.khongTimThayOrder.setVisibility(View.VISIBLE);
                        // resutlt not exits
                    }
                });
        return binding.getRoot();
    }

}