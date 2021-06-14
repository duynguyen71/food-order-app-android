package com.learn.kdnn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

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
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding
                .dosignup
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);
                });


        TextInputEditText etEmail = binding.signInEmail;
        TextInputEditText etPassword = binding.signInPassword;

        binding.btnDoSignIn.setOnClickListener(v -> {
            binding.loginLoader.setVisibility(View.VISIBLE);
            etEmail.setError(null);
            etPassword.setError(null);
            String email = etEmail.getText().toString();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Your email is blank!");
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

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }


}