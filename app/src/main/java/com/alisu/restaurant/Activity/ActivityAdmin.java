package com.alisu.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.alisu.restaurant.Fragment.CartFragment;
import com.alisu.restaurant.Fragment.FragmentAdmin;
import com.alisu.restaurant.Fragment.HomeFragment;
import com.alisu.restaurant.Fragment.MenuAdminFragment;
import com.alisu.restaurant.Fragment.OrderAdminFragment;
import com.alisu.restaurant.Fragment.OrdersFragment;
import com.alisu.restaurant.Fragment.UserFragment;
import com.alisu.restaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityAdmin extends AppCompatActivity {

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_admin);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_admin,new OrderAdminFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.nav_admin_menu :
                    selectedFragment = new MenuAdminFragment();
                    break;

                case R.id.nav_admin_order :
                    selectedFragment = new OrderAdminFragment();
                    break;

                case  R.id.nav_admin    :
                    selectedFragment = new FragmentAdmin();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container_admin, selectedFragment).commit();
            return true;
        }
    };
}
