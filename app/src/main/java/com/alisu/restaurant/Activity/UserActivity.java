package com.alisu.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.alisu.restaurant.Fragment.FavFragment;
import com.alisu.restaurant.Fragment.HomeFragment;
import com.alisu.restaurant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

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
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_home :
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_fav :
                    selectedFragment = new FavFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.container , selectedFragment).commit();
            return true;
        }
    };
}
