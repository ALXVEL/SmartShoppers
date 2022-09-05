package com.example.smartshoppers;

import java.util.ArrayList;
import java.util.Iterator;

public class Store {
    String name;
    ArrayList<Item> itemList;
    ArrayList<User> managerList;
    int distance;

    public Store(String name){
        this.name = name;
        itemList = new ArrayList<Item>();
        managerList = new ArrayList<User>();
        this.distance = (int)(Math.random()*(101));
    }

    public String getName() {
        return name;
    }

    public void addItemToList(Item i){
        itemList.add(i);
    }

    public void addManager(User m){
        managerList.add(m);
    }

    public void removeManager(User m){
        managerList.remove(m);
    }

    public void removeItemFromList(Item i){
        itemList.remove(i);
    }

    public int getDistance(){
        return this.distance;
    }

    public ArrayList<Item> getItemList(){
        return itemList;
    }

    @Override
    public String toString() {
        return this.name + ": (" + this.distance + " km)";
    }
}
