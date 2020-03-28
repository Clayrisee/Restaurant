package com.alisu.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private String orderId,phone,name,table_number,total,status;
    private List<Order> foods;

    public Request() {
    }

    public Request(String orderId,String phone, String name, String table_number, String total, List<Order> foods) {
        this.orderId = orderId;
        this.phone = phone;
        this.name = name;
        this.table_number = table_number;
        this.total = total;
        this.foods = foods;
        this.status = "0"; //Default is 0, 0:order, 1 : order has been made
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
