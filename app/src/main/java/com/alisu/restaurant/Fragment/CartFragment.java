package com.alisu.restaurant.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Adapter.CartAdapter;
import com.alisu.restaurant.Database.Database;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Order;
import com.alisu.restaurant.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests,requests1;

    TextView tvTotalPrice;
    Button btnCheckout;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    String name,phonenumber,total,table_number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");
//        requests1 = database.getReference("Request1");

        //Recycler view
        recyclerView = view.findViewById(R.id.rv_cart);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        tvTotalPrice = view.findViewById(R.id.total);
        btnCheckout = view.findViewById(R.id.btn_checkout);


        getExtra();


        loadListFood();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Request request = new Request(name,phonenumber,table_number,total,cart);
                showAlertDialog();
            }
        });





        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(getContext());
        alertDialog.setTitle("One more step!!");
        alertDialog.setMessage("Enter your table number");

        final EditText etTableNumber = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        etTableNumber.setLayoutParams(layoutParams);
        alertDialog.setView(etTableNumber);//Add edit text to alet dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                table_number = etTableNumber.getText().toString();
                String orderId = String.valueOf(System.currentTimeMillis());
                total = tvTotalPrice.getText().toString();
                Request request = new Request(orderId,phonenumber,name,table_number,total,cart);

                //Submit to firebase
                requests.child(orderId).setValue(request);
                requests1 = database.getReference("Foods");
                requests1.child(orderId).setValue(request.getFoods());

                //Delete cart
                new Database(getActivity().getBaseContext()).cleanCart();
                Toast.makeText(getContext(), "Thank you for your order", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadListFood() {
        cart = new Database(getContext()).getCarts();
        adapter= new CartAdapter(cart,getContext());
        recyclerView.setAdapter(adapter);

        //Calculate Total price
        int total = 0;

        for (Order order:cart){
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("id","ID");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        tvTotalPrice.setText(fmt.format(total));

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
