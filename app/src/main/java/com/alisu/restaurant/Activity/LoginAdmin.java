package com.alisu.restaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alisu.restaurant.R;

public class LoginAdmin extends AppCompatActivity {

    final static String name ="adminglhf";
    final static String pw = "admin123";
    EditText username,password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        username = findViewById(R.id.et_username_admin);
        password = findViewById(R.id.et_password_admin);
        btnLogin = findViewById(R.id.btn_login_admins);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(LoginAdmin.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();

                String currentUsername,currentPassword;
                currentUsername = username.getText().toString();
                currentPassword = password.getText().toString();

                if (currentUsername.equals(name)&&currentPassword.equals(pw)){
                    mDialog.dismiss();
                    Intent intent = new Intent(LoginAdmin.this,ActivityAdmin.class);
                    startActivity(intent);
                    Toast.makeText(LoginAdmin.this, "Login sucessfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(LoginAdmin.this, "Username/Password is wrong! please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
