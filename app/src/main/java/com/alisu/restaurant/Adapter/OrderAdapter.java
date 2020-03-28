package com.alisu.restaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Request;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    public TextView tvOrderId,tvOrderStatus,tvOrderName,tvOrderTable;
    private ArrayList<Request> requestsList;
    private Context context;



    public OrderAdapter(ArrayList<Request> requestList, Context context) {
        this.requestsList = requestList;
        this.context = context;

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_layout,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Request request = requestsList.get(position);
        tvOrderId.setText(request.getOrderId());
        tvOrderName.setText(request.getName());
        tvOrderStatus.setText(converStatus(request.getStatus()));
        tvOrderTable.setText(request.getTable_number());
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {


        public OrderViewHolder(@NonNull View itemView) {


            super(itemView);
            tvOrderId = itemView.findViewById(R.id.order_id);
            tvOrderName = itemView.findViewById(R.id.order_name);
            tvOrderTable = itemView.findViewById(R.id.order_table);
            tvOrderStatus = itemView.findViewById(R.id.order_status);

        }
    }

    private String converStatus(String status){
        if (status.equals("0")){
            return "Pesanda anda telah kami terima";
        }
        else if (status.equals("1")){
            return "Pesanan anda sedang dibuat";
        }
        else if (status.equals("2")){
            return "Pesanan anda telah diantar";
        }
        else {
            return "Pesanan anda telah selesai";
        }
    }
}
