package com.alisu.restaurant.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Adapter.MenuAdapter;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Menu> list;
    MenuAdapter adapter;
    private final String TAG = "Home Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container, false);



        recyclerView = view.findViewById(R.id.rv_menu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<Menu>();




        reference = FirebaseDatabase.getInstance().getReference().child("restaurant");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Menu menu;
                    menu = dataSnapshot1.getValue(Menu.class);
                    list.add(menu);

                }
                adapter = new MenuAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
             }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError);
                Toast.makeText(getActivity(), "Opps... something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        return view ;
    }


}
