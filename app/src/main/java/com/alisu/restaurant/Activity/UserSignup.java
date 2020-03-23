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

import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        Button btnSignup;
        final EditText etName,etPhone,etPassword;

        btnSignup   = findViewById(R.id.btn_register_user);
        etPhone     = findViewById(R.id.et_phone_user);
        etName      = findViewById(R.id.et_register_username);
        etPassword  = findViewById(R.id.et_register_password);

        //Init firebase database
        FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_users = userDatabase.getReference("users");


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(UserSignup.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                final String name,password,phone;
                name        = etName.getText().toString();
                password    = etPassword.getText().toString();
                phone       = etPhone.getText().toString();

                if (TextUtils.isEmpty(phone)){
                    mDialog.dismiss();
                    Toast.makeText(UserSignup.this, "Please input your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)){
                    mDialog.dismiss();
                    Toast.makeText(UserSignup.this, "Please input your password", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(name)){
                    mDialog.dismiss();
                    Toast.makeText(UserSignup.this, "Please input your name", Toast.LENGTH_SHORT).show();
                }

                else {
                    table_users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Check if phone number is already exist
                            if (dataSnapshot.child(phone).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(UserSignup.this, "Phone number is already exist", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserSignup.this, LoginUser.class);
                                startActivity(intent);
                                finish();
                            } else {
                                mDialog.dismiss();
                                Users users = new Users(name, password);
                                table_users.child(phone).setValue(users);
                                Toast.makeText(UserSignup.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }
}
