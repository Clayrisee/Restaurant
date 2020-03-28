package com.alisu.restaurant.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Activity.OrderDetailAdmin;
import com.alisu.restaurant.Adapter.OrderAdminAdapter;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Order;
import com.alisu.restaurant.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class OrderAdminFragment extends Fragment {

    DatabaseReference reference;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    OrderAdminAdapter adapter;
    Request request,updateRequest;

    ArrayList<Request> list = new ArrayList();
     String KEY;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order,container,false);

        //Init db
        database =FirebaseDatabase.getInstance();
        reference = database.getReference("Request");

        //Rv
        recyclerView = view.findViewById(R.id.rv_admin_order);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    request = dataSnapshot1.getValue(Request.class);
                    list.add(request);
                }
                adapter = new OrderAdminAdapter(list,getContext());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickCallback(new OrderAdminAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(Request request) {
                        Intent intent = new Intent(getActivity(), OrderDetailAdmin.class);
                        intent.putExtra("orderid",request.getOrderId());
                        intent.putExtra("name",request.getName());
                        intent.putExtra("status",request.getStatus());
                        intent.putExtra("table_number",request.getTable_number());
//                        ArrayList<Order> foods = new ArrayList<>(request.getFoods().size());
//                        foods.addAll(request.getFoods());
//                        intent.putExtra("foods",foods);
                        startActivity(intent);


                    }
                });
                adapter.setOnItemLongClickCallback(new OrderAdminAdapter.OnItemLongClickCallback() {
                    @Override
                    public void onItemLongClicked(Request request) {
                        KEY = request.getOrderId();
                        updateRequest = new Request(request.getOrderId(),request.getPhone(),request.getName(),request.getTable_number(),request.getTotal(),request.getFoods());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Update")){
            showUpdateDialog(KEY,updateRequest);


        }

        else if(item.getTitle().equals("Done")){

            reference.child(KEY).removeValue();
            list.clear();



        }

        return super.onContextItemSelected(item);

    }

    private void showUpdateDialog(final String Key, final Request request) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Update Menu");
        alertDialog.setMessage("Please");

        LayoutInflater inflater = this.getLayoutInflater();
        View update= inflater.inflate(R.layout.update_status,null);

        //updated status
        Spinner statusList = update.findViewById(R.id.spinner_status);
        String [] status ={"Pesanan masuk","Pesanan sedang dibuat","Pesanan telah diantar","Pesanan selesai"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, status);
        statusList.setAdapter(arrayAdapter);

        statusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currentStatus = arrayAdapter.getItem(i);
                assert currentStatus != null;
                request.setStatus(converStatus(currentStatus));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertDialog.setView(update);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                //updated information


                reference.child(Key).setValue(request);
                list.clear();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
    private String converStatus(String status){
        if (status.equals("Pesanan masuk")){
            return "0";
        }
        else if (status.equals("Pesanan sedang dibuat")){
            return "1";
        }
        else if (status.equals("Pesanan telah diantar")){
            return "2";
        }
        else {
            return "3";
        }
    }
}
