package com.alisu.restaurant.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Adapter.OrderAdapter;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

    DatabaseReference reference;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    String name,phonenumber;
    ArrayList<Request> list = new ArrayList<>();
    OrderAdapter adapter;
    TextView tvOrderId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders,container, false);

        //Init database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Request");

        //tv id
        tvOrderId = view.findViewById(R.id.order_id);

        //Recycler view
        recyclerView = view.findViewById(R.id.rv_orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //get extras
        getExtra();

        reference.orderByChild("phone").equalTo(phonenumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Request menu;
                    menu = dataSnapshot1.getValue(Request.class);
                    list.add(menu);
                }
                adapter = new OrderAdapter(list,getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
