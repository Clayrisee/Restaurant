package com.alisu.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alisu.restaurant.Adapter.OrderDetailAdminAdapter;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdmin extends AppCompatActivity {

    String orderId,name,status,table_number;
    ArrayList<Order> orderList;

    TextView tvOrderId,tvName,tvTableNumber;
//    EditText etStatus;

    DatabaseReference reference;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    OrderDetailAdminAdapter adapter;

    ArrayList<Order> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_admin);

        tvOrderId = findViewById(R.id.detail_order_id);
        tvName    = findViewById(R.id.order_name_detail);
        tvTableNumber = findViewById(R.id.order_number_detail);

        getExtra();

        tvOrderId.setText(orderId);
        tvTableNumber.setText(table_number);
        tvName.setText(name);

        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Foods");


        recyclerView = findViewById(R.id.rv_detail_menu);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        list = new ArrayList<Order>();
        DatabaseReference rvReference = reference.child(orderId);
        rvReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Order order;
                    order = dataSnapshot1.getValue(Order.class);
                    list.add(order);

                }
                adapter = new OrderDetailAdminAdapter(list,OrderDetailAdmin.this);
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void getExtra(){
        Intent intent = getIntent();
        this.orderId = intent.getStringExtra("orderid");
        this.name = intent.getStringExtra("name");
        this.status = intent.getStringExtra("status");
        this.table_number = intent.getStringExtra("table_number");
//        this.orderList = (ArrayList<Order>) intent.getSerializableExtra("foods");
    }
}
