package com.alisu.restaurant.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alisu.restaurant.Activity.LoginAdmin;
import com.alisu.restaurant.R;

import java.util.Objects;
import java.util.zip.Inflater;

public class FragmentAdmin extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin,container, false);

        Button btnLogout = view.findViewById(R.id.btn_logout_admin);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginAdmin.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return view;
    }
}
