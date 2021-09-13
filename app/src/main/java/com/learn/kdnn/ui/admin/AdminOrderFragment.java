package com.learn.kdnn.ui.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learn.kdnn.R;

import org.jetbrains.annotations.NotNull;


public class AdminOrderFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public AdminOrderFragment() {
    }


    public static AdminOrderFragment newInstance() {
        AdminOrderFragment fragment = new AdminOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);

        FirebaseDatabase.getInstance().getReference("orders").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Log.e(TAG, "onDataChange: "+snapshot.getValue().toString() );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                }
        );

        return view;

    }
}