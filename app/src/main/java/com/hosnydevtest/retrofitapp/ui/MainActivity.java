package com.hosnydevtest.retrofitapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hosnydevtest.retrofitapp.R;
import com.hosnydevtest.retrofitapp.fragments.HomeFragment;
import com.hosnydevtest.retrofitapp.fragments.SettingsFragment;
import com.hosnydevtest.retrofitapp.fragments.UsersFragment;
import com.hosnydevtest.retrofitapp.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        displayFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_users:
                fragment = new UsersFragment();
                break;
            case R.id.menu_settings:
                fragment = new SettingsFragment();
                break;
        }
        if (fragment != null)
            displayFragment(fragment);

        return false;
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}