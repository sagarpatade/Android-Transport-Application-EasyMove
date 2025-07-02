package com.example.easymove;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Load default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        Fragment selectedFragment = null;

        if (menuItem.getItemId() == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (menuItem.getItemId() == R.id.nav_order) {
            selectedFragment = new OrderFragment();
        } else if (menuItem.getItemId() == R.id.nav_payments) {
            selectedFragment = new PaymentsFragment();
        } else if (menuItem.getItemId() == R.id.nav_account) {
            selectedFragment = new AccountFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}
