package com.example.smartshoppers;

import javafx.collections.ObservableArray;

public class Item {
    String name, category;
    double price;
    int quantity;
    boolean onSale;
    Store store;

    Item(String name, double price, int quantity, String category, Store store){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.onSale = false;
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public boolean isOnSale(){
        return this.onSale;
    }

    public void setSale(boolean b){
        if(b){
            this.onSale = true;
        }else{
            this.onSale = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
