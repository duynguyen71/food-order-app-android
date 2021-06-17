package com.learn.kdnn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.learn.kdnn.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        TextInputEditText etEmail = binding.signUpEmail;
        TextInputEditText etPassword = binding.signUpPassword;
        binding.closeRegister.setOnClickListener(v->{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnSignUp.setOnClickListener(v -> {
            hideKeyBoard();
            binding.registerLoader.setVisibility(View.VISIBLE);
            etEmail.setError(null);
            etPassword.setError(null);

            String email = etEmail.getText().toString();
            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is blank!");
                binding.registerLoader.setVisibility(View.GONE);
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("Your email is not valid!");
                binding.registerLoader.setVisibility(View.GONE);
                return;
            }

            String pass = etPassword.getText().toString();
            if (TextUtils.isEmpty(pass)) {
                etPassword.setError("Password is bank!");
                binding.registerLoader.setVisibility(View.GONE);
                return;
            }

            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(authResult -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        this.finish();
                    })
                    .addOnFailureListener(e -> {
                        Log.d(TAG, "createUserWithEmailAndPassword: ", e.fillInStackTrace());
                        etEmail.setError("Your email has been used!");
                        etPassword.setText("");
                        binding.registerLoader.setVisibility(View.GONE);

                    });

        });
    }
    private void hideKeyBoard() {
        InputMethodManager manager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        View focus = getCurrentFocus();
        if(focus==null){
            return;
        }
        manager.hideSoftInputFromWindow(focus.getWindowToken(),0);
    }
}