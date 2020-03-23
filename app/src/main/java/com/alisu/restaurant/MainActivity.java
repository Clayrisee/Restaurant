package com.alisu.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alisu.restaurant.Activity.LoginAdmin;
import com.alisu.restaurant.Activity.LoginUser;

public class MainActivity extends AppCompatActivity {

    Button loginUser,loginAdmin;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUser = findViewById(R.id.btn_login_user);
        loginAdmin = findViewById(R.id.btn_login_admin);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
            }
        });

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
            }
        });


    }


}
