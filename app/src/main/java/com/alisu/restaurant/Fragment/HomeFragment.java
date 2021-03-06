package com.alisu.restaurant.Fragment;

import android.content.Intent;
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

import com.alisu.restaurant.Activity.DetailActivity;
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
    FirebaseDatabase database;
    RecyclerView recyclerView;
    ArrayList<Menu> list;
    MenuAdapter adapter;
    private final String TAG = "Home Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container, false);


        //Init database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("restaurant");

        //Recycler view
        recyclerView = view.findViewById(R.id.rv_menu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
//        loadMenu();

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

                adapter.setOnItemClickCallback(new MenuAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(Menu menu) {

                        final String harga = String.valueOf(menu.getHarga());

                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("nama",menu.getNama());
                        intent.putExtra("description",menu.getDescription());
                        intent.putExtra("url",menu.getUrl());
                        intent.putExtra("harga",harga);
                        intent.putExtra("id",menu.getId());
                        startActivity(intent);

                    }
                });
             }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError);
                Toast.makeText(getActivity(), "Opps... something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        return view ;
    }

//    private void loadMenu() {
//
//
//        FirebaseRecyclerOptions<Menu> menu = new FirebaseRecyclerOptions.Builder<Menu>()
//                                                .setQuery(reference,Menu.class).build();
//
//        FirebaseRecyclerAdapter<Menu,MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(menu) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i, @NonNull final Menu menu) {
//
//                String harga = String.valueOf(menu.getHarga());
//                Picasso.get().load(menu.getUrl()).into(menuViewHolder.ivMenu);
//                menuViewHolder.name.setText(menu.getNama());
//                menuViewHolder.price.setText(harga);
//                menuViewHolder.desc.setText(menu.getDescription());
//
//                menuViewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getActivity(), menu.getNama(), Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent()
//                    }
//                });
//
//
//            }
//
//            @NonNull
//            @Override
//            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(getContext()).inflate(R.layout.list_menu,parent,false);
//                return new MenuViewHolder(view);
//            }
//
//
//        };
//        recyclerView.setAdapter(adapter);
//    }


}
