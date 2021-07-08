package com.learn.kdnn.ui.account;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.learn.kdnn.AccountActivity;
import com.learn.kdnn.databinding.FragmentAccountMainBinding;
import com.learn.kdnn.utils.ApplicationUiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class AccountMainFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private FirebaseAuth auth;
    private FragmentAccountMainBinding binding;
    private FirebaseUser currentUser;
    private AccountActivity accountActivity;
    private AccountViewModel viewModel;

    public AccountMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        accountActivity = ((AccountActivity) getContext());
        binding = FragmentAccountMainBinding.inflate(inflater, container, false);
        registerListenerViewModel();




        binding.usernameLayout.setEndIconOnClickListener(v -> {
            binding.etUsername.setEnabled(true);
            binding.etUsername.requestFocus();
            if (binding.etUsername.getText() != null) {
                binding.etUsername.setSelection(binding.etUsername.getText().toString().length());
            }
            ApplicationUiUtils.showSoftInput(accountActivity, binding.etUsername);

        });

        binding.addressLayout.setEndIconOnClickListener(v -> {

            binding.etAddress.setEnabled(true);
            binding.etAddress.requestFocus();
            if (binding.etAddress.getText() != null) {
                binding.etAddress.setSelection(binding.etAddress.getText().toString().length());
            }
            ApplicationUiUtils.showSoftInput(accountActivity, binding.etAddress);

        });

        binding.phoneLayout.setEndIconOnClickListener(v -> {
            binding.etPhone.setEnabled(true);
            binding.etPhone.requestFocus();
            if (binding.etPhone.getText() != null) {
                binding.etPhone.setSelection(binding.etPhone.getText().toString().length());
            }
            ApplicationUiUtils.showSoftInput(accountActivity, binding.etPhone);

        });

        binding.btnUpdate.setOnClickListener(v -> handleUpdateUserInfo(binding.etUsername.getText().toString().trim(), binding.etAddress.getText().toString().trim(), binding.etPhone.getText().toString().trim()));


        return binding.getRoot();
    }

    private void registerListenerViewModel() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.etUsername.setText(currentUser.getDisplayName());
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        viewModel.getUserInfo().observe(requireActivity(), userInfo -> {
            if (userInfo != null) {
                binding.etPhone.setText(userInfo.getPhoneNumber());
                binding.etAddress.setText(userInfo.getAddress());
            }
        });

    }

    public void handleUpdateUserInfo(String iusername, String iaddress, String iphone) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Map<String, Object> userMap = new HashMap<>();
        //iusername
        if (iusername.length() > 0 && !iusername.trim().equals(currentUser.getDisplayName())) {
            //update
            updateUsername(iusername);
        } else {
            binding.etUsername.setText(currentUser.getDisplayName());
        }
        //iaddress and iphone
        if (iaddress.length() > 0 && !iaddress.equals(viewModel.getUserInfo().getValue().getAddress())) {
            userMap.put("address", iaddress);
        } else {
            binding.etAddress.setText(viewModel.getUserInfo().getValue().getAddress());
        }

        if (iphone.length() > 0 && !iphone.equals(viewModel.getUserInfo().getValue().getPhoneNumber())) {
            userMap.put("phone", iphone);
        } else {
            binding.etPhone.setText(viewModel.getUserInfo().getValue().getPhoneNumber());
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(auth.getUid())
                .set(userMap, SetOptions.merge());

        binding.etPhone.setEnabled(false);
        binding.etAddress.setEnabled(false);
        binding.etUsername.setEnabled(false);

    }

    private void updateUsername(String username) {
        UserProfileChangeRequest build =
                new UserProfileChangeRequest.Builder().setDisplayName(username).build();

        FirebaseAuth.getInstance().getCurrentUser()
                .updateProfile(build);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}