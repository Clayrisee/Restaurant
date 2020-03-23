package com.alisu.restaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alisu.restaurant.R;
import com.alisu.restaurant.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    ArrayList<Menu> menus;
    Context context;
    private OnItemClickCallback onItemClickCallback;
    public MenuAdapter(ArrayList<Menu> m,Context c){

       context = c;
       menus = m;

    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_menu,parent,false);
        return new MenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, final int position) {
        Menu menu = menus.get(position);
        final String harga = String.valueOf(menu.getHarga());
        Picasso.get().load(menu.getUrl()).into(holder.ivMenu);
        holder.name.setText(menu.getNama());
        holder.price.setText(harga);
        holder.desc.setText(menu.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(menus.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }




    class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,desc;
        ImageView ivMenu;

        public MenuViewHolder(View v){
            super(v);

            name = v.findViewById(R.id.tv_nama);
            price = v.findViewById(R.id.tv_harga);
            desc = v.findViewById(R.id.tv_desc);
            ivMenu = v.findViewById(R.id.iv_menu);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Menu menu);
    }
}
