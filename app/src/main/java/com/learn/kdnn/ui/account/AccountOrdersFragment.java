package com.learn.kdnn.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.kdnn.databinding.FragmentAccountOrdersBinding;
import com.learn.kdnn.model.Order;

import java.util.ArrayList;
import java.util.List;


public class AccountOrdersFragment extends Fragment {

    private FragmentAccountOrdersBinding binding;

    private boolean reverseLayout = false;

    public AccountOrdersFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountOrdersBinding.inflate(inflater, container, false);

        FirebaseDatabase
                .getInstance()
                .getReference("orders")
                .child(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot rs = task.getResult();
                        List<Order> orderList = new ArrayList<>();
                        rs.getChildren().forEach(snapshot -> {
                            orderList.add((Order) snapshot.getValue(Order.class));
                        });

                        setUpRecyclerView(orderList);
                        Log.d("TAG", "onCreateView: " + orderList.toString());
                    }
                });


        return binding.getRoot();
    }

    void setUpRecyclerView(List<Order> orderList) {

        RecyclerView rcv = binding.rcvAccountOrders;
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, reverseLayout));
        OrderItemAdapter adapter = new OrderItemAdapter(orderList, getContext());
        rcv.setAdapter(adapter);

    }
}