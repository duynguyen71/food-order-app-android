package com.learn.kdnn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.learn.kdnn.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            this.startMainActivity();
            return;
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.closeLogin.setOnClickListener(v -> {
            startMainActivity();
        });
        setContentView(binding.getRoot());
        binding
                .dosignup
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);
                });

        TextInputEditText etEmail = binding.signInEmail;
        TextInputEditText etPassword = binding.signInPassword;

        binding.btnDoSignIn.setOnClickListener(v -> {
            hideKeyBoard();
            binding.loginLoader.setVisibility(View.VISIBLE);
            etEmail.setError(null);
            etPassword.setError(null);
            String email = etEmail.getText().toString();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Your email is blank!");
                binding.loginLoader.setVisibility(View.GONE);
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Your email is not valid!");
                binding.loginLoader.setVisibility(View.GONE);
                return;
            }
            String pass = etPassword.getText().toString();
            if (TextUtils.isEmpty(pass)) {
                etPassword.setError("Your password is blank!");
                binding.loginLoader.setVisibility(View.GONE);

                return;
            }

            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, pass)
                    .addOnFailureListener(e -> {
                        binding.loginLoader.setVisibility(View.GONE);
                        etEmail.setError("Email or password is not valid!");
                        etPassword.setText(null);
                    })
                    .addOnSuccessListener(auth -> {
                        startMainActivity();
                    });
        });

    }

    private void hideKeyBoard() {
        InputMethodManager manager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        View focus = getCurrentFocus();
        if (focus == null) {
            return;
        }
        manager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }


}