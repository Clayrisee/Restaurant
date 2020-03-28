package com.alisu.restaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alisu.restaurant.Database.Database;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Order;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    String name,harga,description,url,quantity,id;
    Button btnAddChart,plus,minus;
    TextView tvName,tvHarga,tvDesc;
    EditText etQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getExtra();
        set();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incNumber();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decNumber();
            }
        });



        btnAddChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                harga = String.valueOf((Integer.parseInt(harga)*Integer.parseInt(quantity)));
                Order order = new Order(name,quantity,harga,id);
                order.setId(id);
                order.setName(name);
                order.setPrice(harga);
                order.setQuantity(quantity);
                new Database(getBaseContext()).addToCart(order);

                Toast.makeText(DetailActivity.this, "Your add "+name+" to cart", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

    }

    private void getExtra(){
        Intent intent = getIntent();
        name = intent.getStringExtra("nama");
        harga = intent.getStringExtra("harga");
        description = intent.getStringExtra("description");
        url = intent.getStringExtra("url");
        id = intent.getStringExtra("id");
    }

    private void set(){
        tvName = findViewById(R.id.nama_menu);
        tvHarga = findViewById(R.id.harga);
        tvDesc = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.gambar_menu);
        btnAddChart = findViewById(R.id.btn_add);
        plus = findViewById(R.id.increase);
        minus = findViewById(R.id.decrease);
        etQuantity = findViewById(R.id.integer_number);


        tvName.setText(this.name);
        tvDesc.setText(this.description);
        tvHarga.setText(this.harga);
        Picasso.get().load(this.url).into(imageView);
    }

    private void incNumber(){
        int inc = Integer.parseInt(etQuantity.getText().toString());
        display(inc + 1);
        quantity = etQuantity.getText().toString();
    }
    private void decNumber(){
        int inc = Integer.parseInt(etQuantity.getText().toString());
        display(inc - 1);
        quantity = etQuantity.getText().toString();
    }
    private void display(int number){
        String quan= String.valueOf(number);
        etQuantity.setText(quan);
    }

}
