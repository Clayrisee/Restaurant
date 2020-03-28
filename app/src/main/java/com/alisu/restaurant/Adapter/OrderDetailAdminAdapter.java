package com.alisu.restaurant.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.Activity.OrderDetailAdmin;
import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Order;
import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;

public class OrderDetailAdminAdapter extends RecyclerView.Adapter<OrderDetailAdminAdapter.ViewHolder> {

    private ArrayList<Order> orderList = new ArrayList<>();
    private Context context;

//    private OnItemClickCallback onItemClickCallback;


    public OrderDetailAdminAdapter(ArrayList<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+orderList.get(position).getQuantity(), Color.RED);
        holder.ivQuantity.setImageDrawable(drawable);
        holder.tvMenu.setText(orderList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMenu;
        public ImageView ivQuantity;


        public void setTvMenu(TextView tvMenu) {
            this.tvMenu = tvMenu;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.order_detail_food_menu);
            ivQuantity = itemView.findViewById(R.id.order_detail_quantity);
        }
    }
}
