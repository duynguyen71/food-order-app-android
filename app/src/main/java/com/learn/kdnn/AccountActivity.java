package com.learn.kdnn;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.learn.kdnn.databinding.ActivityAccountBinding;
import com.learn.kdnn.databinding.DialogChooseMgBinding;
import com.learn.kdnn.ui.account.AccountViewModel;
import com.learn.kdnn.ui.account.SectionsPagerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private CircleImageView avatar;

    public static final String ARG_VIEW_PAGER_POS = "viewPagerPosition";
    private Integer pagerPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        com.learn.kdnn.databinding.ActivityAccountBinding binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new ViewModelProvider(this).get(AccountViewModel.class);
        ViewPager viewPager = setUpViewPager(binding);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            pagerPosition = bundle.getInt(ARG_VIEW_PAGER_POS);
        }

        viewPager.setCurrentItem(pagerPosition);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Uri photoUrl = currentUser.getPhotoUrl();
            avatar = binding.avatar;
            loadImgAvatar(photoUrl, avatar);
        }
        binding.btnUpdateImage.setOnClickListener(v -> handleUpdateImage());
        binding.btnCloseAccountActivity.setOnClickListener(v -> handleBtnClose());

    }

    private ViewPager setUpViewPager(ActivityAccountBinding binding) {
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        return viewPager;
        //
    }

    private void handleUpdateImage() {
        Dialog dialog = new Dialog(this);
        DialogChooseMgBinding binding1 = DialogChooseMgBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.show();
        binding1.etImgURL.setText("https://images.pexels.com/photos/774731/pexels-photo-774731.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500");
        binding1.saveImg.setOnClickListener(v1 -> {
            String URL = binding1.etImgURL.getText().toString();
            if (TextUtils.isEmpty(URL)) {
                return;
            }
            loadImgAvatar(Uri.parse(URL), this.avatar);
            Handler handler = new Handler();
            Thread thread = new Thread(() -> updateUserImage(URL, handler));
            thread.start();

            dialog.dismiss();
        });
    }

    private void handleBtnClose() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void loadImgAvatar(Uri photoUrl, CircleImageView avatar) {
        Glide.with(this)
                .load(photoUrl)
                .centerCrop()
                .into(avatar);
    }

    private void updateUserImage(String URL, Handler handler) {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            return;
        }
        UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(URL))
                .build();

        user.updateProfile(updateProfile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        handler.post(() -> {

                            Toast.makeText(this, "Update Image Success!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

    private void updateUserDisplayName(String name, Handler handler) {
        FirebaseUser user = auth.getCurrentUser();
        UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(updateProfile)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        handler.post(() -> {
                            Toast.makeText(this, "Update Name Success!", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
    }

}