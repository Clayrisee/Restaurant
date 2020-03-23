package com.alisu.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.alisu.restaurant.Fragment.CartFragment;
import com.alisu.restaurant.Fragment.HomeFragment;
import com.alisu.restaurant.Fragment.UserFragment;
import com.alisu.restaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.nav_home :
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_cart :
                    selectedFragment = new CartFragment();
                    getBundles(selectedFragment);
                    break;

                case R.id.nav_user :
                    selectedFragment = new UserFragment();
                    getBundles(selectedFragment);
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container , selectedFragment).commit();
            return true;
        }
    };

    private void getBundles(Fragment selectedFragment){
        Intent extra = getIntent();
        Bundle bundle = new Bundle();
        String nama =  extra.getStringExtra("name");
        String phone = extra.getStringExtra("phone");
        bundle.putString("name",nama);
        bundle.putString("phone",phone);
        selectedFragment.setArguments(bundle);

    }
}
