package com.alisu.restaurant.Adapter;

import android.content.Context;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {


    ArrayList<Menu> menus;
    Context context;
    public MenuAdapter(ArrayList<Menu> m,Context c){

       context = c;
       menus = m;

    }



    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_menu,parent,false);
        return new MenuViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menus.get(position);
        String harga = String.valueOf(menu.getHarga());
        Picasso.get().load(menu.getUrl()).into(holder.ivMenu);
        holder.name.setText(menu.getNama());
        holder.price.setText(harga);
        holder.desc.setText(menu.getDescription());


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
}
