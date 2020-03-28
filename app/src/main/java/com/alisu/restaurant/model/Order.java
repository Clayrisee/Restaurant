package com.alisu.restaurant.model;

public class Order {
    String name, quantity, price, id;

    public Order(String name, String quantity, String price, String id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}