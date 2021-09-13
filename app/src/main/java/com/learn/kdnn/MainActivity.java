package com.learn.kdnn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.learn.kdnn.databinding.ActivityMainBinding;
import com.learn.kdnn.model.ERole;
import com.learn.kdnn.ui.admin.HahaActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        updateBagCounter();

        setSupportActionBar(binding.appBarMain.toolbar);


//        TODO: fix
        binding.appBarMain.accountCircle.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, binding.appBarMain.accountCircle);
            popupMenu.inflate(R.menu.account_setting);
            popupMenu.show();
            boolean isLogged = FirebaseAuth.getInstance().getCurrentUser() == null;
            if (!isLogged) {
                MenuItem logout = popupMenu.getMenu().getItem(2);
                logout.setTitle("Login");
            }
            popupMenu.setOnMenuItemClickListener(item -> handleAccountPopupItemMenuClick(item));
        });

        binding.appBarMain.dashboard.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth != null) {
                boolean isAdmin = viewModel.getUser().getValue().getRole().equals(ERole.ROLE_ADMIN.name());
                if (isAdmin) {
                    Intent intent = new Intent(this, HahaActivity.class);
                    startActivity(intent);
                }
            } else {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
//        set up bottom nav
        NavigationUI.setupWithNavController(binding.appBarMain.contentMainContainer.bottomNavView, getMainNavController());
    }

    private boolean handleAccountPopupItemMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout: {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                this.finish();
                break;
            }
            case R.id.item_manage: {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent i = new Intent(this, AccountActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivity(i);
                    this.finish();
                }
                break;
            }

        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public NavController getMainNavController() {
        return Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    }

    public void updateBagCounter() {
        MutableLiveData<HashMap<Long, Object>> bag = viewModel.getBag();
        if (bag == null) {
            return;
        }
        HashMap<Long, Object> value = bag.getValue();
        binding.appBarMain.countBagItem.setText(String.valueOf(value.size()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}