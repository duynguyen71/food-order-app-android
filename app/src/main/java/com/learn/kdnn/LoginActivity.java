package com.learn.kdnn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.learn.kdnn.databinding.ActivityLoginBinding;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private ActivityLoginBinding binding;
    private final int RC_GG_SIGN_IN = 343;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check user login or not
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            this.startMainActivity();
            return;
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.closeLogin.setOnClickListener(v -> startMainActivity());

        // sign in with google
        binding.btnSignInWithGG.setOnClickListener(v -> {
            binding.loginLoader.setVisibility(View.VISIBLE);
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.gg_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

            Intent i = googleSignInClient.getSignInIntent();
            startActivityForResult(i, RC_GG_SIGN_IN);
        });






        binding.btnDoSignIn.setOnClickListener(v -> {
            TextInputEditText etEmail = binding.signInEmail;
            TextInputEditText etPassword = binding.signInPassword;
            hideSoftInputMethod();
            binding.loginLoader.setVisibility(View.VISIBLE);
            etEmail.setError(null);
            etPassword.setError(null);
            String email = Objects.requireNonNull(etEmail.getText()).toString();
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
            String pass = Objects.requireNonNull(etPassword.getText()).toString();
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
                        return;
                    })
                    .addOnSuccessListener(auth -> startMainActivity());
        });


        //register
        binding
                .dosignup
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);
                });


    }

    private void hideSoftInputMethod() {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.loginLoader.setVisibility(View.GONE);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GG_SIGN_IN && resultCode == Activity.RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        startMainActivity();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }


}