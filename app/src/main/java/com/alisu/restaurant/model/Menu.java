package com.alisu.restaurant.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Menu {
    private String nama,description,url;
    private int harga;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public Menu() {
    }

    public Menu(String nama, String description, String url, int harga) {
        this.nama = nama;
        this.description = description;
        this.url = url;
        this.harga = harga;
    }




}
