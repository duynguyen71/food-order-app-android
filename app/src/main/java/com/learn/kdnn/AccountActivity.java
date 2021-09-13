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

    }



}