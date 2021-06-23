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
import com.learn.kdnn.utils.MyUiUtil;

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
            MyUiUtil.showSoftInput(accountActivity, binding.etUsername);

        });

        binding.addressLayout.setEndIconOnClickListener(v -> {

            binding.etAddress.setEnabled(true);
            binding.etAddress.requestFocus();
            if (binding.etAddress.getText() != null) {
                binding.etAddress.setSelection(binding.etAddress.getText().toString().length());
            }
            MyUiUtil.showSoftInput(accountActivity, binding.etAddress);

        });

        binding.phoneLayout.setEndIconOnClickListener(v -> {
            binding.etPhone.setEnabled(true);
            binding.etPhone.requestFocus();
            if (binding.etPhone.getText() != null) {
                binding.etPhone.setSelection(binding.etPhone.getText().toString().length());
            }
            MyUiUtil.showSoftInput(accountActivity, binding.etPhone);

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

    public void handleUpdateUserInfo(String username, String address, String phone) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Map<String, Object> userMap = new HashMap<>();
        //username
        if (username.length() > 0 && !username.trim().equals(currentUser.getDisplayName())) {
            //update
            updateUsername(username);
        } else {
            binding.etUsername.setText(currentUser.getDisplayName());
        }
        //address and phone
        if (address.length() > 0 && !address.equals(viewModel.getUserInfo().getValue().getAddress())) {
            userMap.put("address", address);
        } else {
            binding.etAddress.setText(viewModel.getUserInfo().getValue().getAddress());
        }

        if (phone.length() > 0 && !phone.equals(viewModel.getUserInfo().getValue().getPhoneNumber())) {
            userMap.put("phone", phone);
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