package com.alisu.restaurant.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Menu {
    private String nama,description,url,id;
    private int harga;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Menu(String nama, String description, String url, String id, int harga) {
        this.nama = nama;
        this.description = description;
        this.url = url;
        this.id = id;
        this.harga = harga;
    }
}
