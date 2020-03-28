package com.alisu.restaurant.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alisu.restaurant.Activity.LoginUser;
import com.alisu.restaurant.R;

import java.util.Objects;

public class UserFragment extends Fragment {
    Button btnLogout;
    TextView username,phone;
    String name,phonenumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        phone = view.findViewById(R.id.user_phone);
        username = view.findViewById(R.id.user_name);

        getExtra();
        username.setText(name);
        phone.setText(phonenumber);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginUser.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return view;
    }

    private void getExtra(){
        //Instance bundle
        Bundle bundle = this.getArguments();

        //Check apakah bundle null atau tidak
        if (bundle != null){
            name        = bundle.getString("name");
            phonenumber = bundle.getString("phone");
        }
    }


}
