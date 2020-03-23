package com.alisu.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alisu.restaurant.Common.Common;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText etPhone,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnLogin    = findViewById(R.id.btn_login_users);
        btnRegister = findViewById(R.id.btn_signup);
        etPhone = findViewById(R.id.et_phone_users);
        etPassword  = findViewById(R.id.et_password_user);

        //Init firebase database
        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_users = userDatabase.getReference("users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(LoginUser.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                //Check if field is empty

                final String phone,password;
                phone    = etPhone.getText().toString();
                password = etPassword.getText().toString();



                if (TextUtils.isEmpty(phone)){
                    mDialog.dismiss();
                    Toast.makeText(LoginUser.this, "Please input your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)){
                    mDialog.dismiss();
                    Toast.makeText(LoginUser.this, "Please input your password", Toast.LENGTH_SHORT).show();
                }
                else {
                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //check if users doesn't exist
                        if (dataSnapshot.child(phone).exists()) {

                            mDialog.dismiss();
                            //Get user information
                            Users users = dataSnapshot.child(phone).getValue(Users.class);

                            if (users != null) {
                                String usersPassword = users.getPassword();
                                if (usersPassword.equals(password.toString())) {


                                    Toast.makeText(LoginUser.this, "Login Succesfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginUser.this,UserActivity.class);
                                    intent.putExtra("phone",etPhone.getText().toString());
                                    intent.putExtra("name",users.getName());
                                    Common.currentUser = users;
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginUser.this, "Login failed, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(LoginUser.this, "User not exist in Database, Please register", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginUser.this,UserSignup.class);
                startActivity(i);
            }
        });



    }




}
