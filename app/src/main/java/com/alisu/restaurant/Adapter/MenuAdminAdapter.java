package com.alisu.restaurant.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuAdminAdapter extends RecyclerView.Adapter<MenuAdminAdapter.ViewHolder>{

    ArrayList<Menu> menus;
    Context context;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public MenuAdminAdapter(ArrayList<Menu> menus, Context context) {
        this.menus = menus;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_menu,parent,false);
        return new MenuAdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuAdminAdapter.ViewHolder holder, int position) {
        Menu menu = menus.get(position);
        final String harga = String.valueOf(menu.getHarga());
        Picasso.get().load(menu.getUrl()).into(holder.ivMenu);
        holder.name.setText(menu.getNama());
        holder.price.setText(harga);
        holder.desc.setText(menu.getDescription());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickCallback.onItemClicked(menus.get(holder.getAdapterPosition()));

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return menus.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name,price,desc;
        ImageView ivMenu;

        public ViewHolder(View v){
            super(v);

            name = v.findViewById(R.id.tv_nama);
            price = v.findViewById(R.id.tv_harga);
            desc = v.findViewById(R.id.tv_desc);
            ivMenu = v.findViewById(R.id.iv_menu);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select the action");
            contextMenu.add(0,0,getAdapterPosition(),"Update");
            contextMenu.add(0,0,getAdapterPosition(),"Delete");


        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Menu menu);
    }
}
