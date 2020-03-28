package com.alisu.restaurant.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Request;

import java.util.ArrayList;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.OrderAdminViewHolder> {

    ArrayList<Request> requestArrayList;
    Context context;
    private OnItemClickCallback onItemClickCallback;
    private OnItemLongClickCallback onItemLongClickCallback;

    public void setOnItemLongClickCallback(OnItemLongClickCallback onItemLongClickCallback) {
        this.onItemLongClickCallback = onItemLongClickCallback;
    }

    TextView tvOrderId,tvOrderStatus,tvOrderName,tvOrderTable;

    public OrderAdminAdapter(ArrayList<Request> requestArrayList, Context context) {
        this.requestArrayList = requestArrayList;
        this.context = context;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public OrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_layout_admin,parent,false);
        return new  OrderAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdminViewHolder holder, int position) {
        final Request request = requestArrayList.get(position);
        tvOrderId.setText(request.getOrderId());
        tvOrderName.setText(request.getName());
        tvOrderStatus.setText(converStatus(request.getStatus()));
        tvOrderTable.setText(request.getTable_number());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(requestArrayList.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemLongClickCallback.onItemLongClicked(requestArrayList.get(holder.getAdapterPosition()));
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class OrderAdminViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{



        public OrderAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.order_id_admin);
            tvOrderName = itemView.findViewById(R.id.order_name_admin);
            tvOrderTable = itemView.findViewById(R.id.order_table_admin);
            tvOrderStatus = itemView.findViewById(R.id.order_status_admin);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select the action");
            contextMenu.add(0,0,getAdapterPosition(),"Update");
            contextMenu.add(0,0,getAdapterPosition(),"Done");
        }
    }


    public interface OnItemClickCallback{
        void onItemClicked(Request request);
    }

    public interface OnItemLongClickCallback{
        void onItemLongClicked(Request request);
    }

    private String converStatus(String status){
        if (status.equals("0")){
            return "Pesanan masuk";
        }
        else if (status.equals("1")){
            return "Pesanan sedang dibuat";
        }
        else if (status.equals("2")){
            return "Pesanan telah diantar";
        }
        else {
            return "Pesanan telah selesai";
        }
    }
}
