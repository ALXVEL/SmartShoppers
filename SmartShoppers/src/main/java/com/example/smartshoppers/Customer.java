package com.example.smartshoppers;

import java.util.HashSet;
import java.util.Iterator;

public class Customer implements User{
    String id, pass;
    HashSet<Item> shoppingList;
    HashSet<Store> savedLocations;

    Customer(String id, String pass){
        this.id = id;
        this.pass = pass;
        shoppingList = new HashSet<Item>();
        savedLocations = new HashSet<Store>();
    }

    public void addToShoppingList(Item i){
        shoppingList.add(i);
    }

    public HashSet<Item> getShoppingList(){
        return shoppingList;
    }

    public void addToSavedLocations(Store s){
        savedLocations.add(s);
    }

    public HashSet<Store> getSavedLocations(){
        return savedLocations;
    }

    public void removeFromSavedLocation(String s){
        for(Store store: savedLocations){
            if(store.getName().equals(s)){
                savedLocations.remove(store);
            }
        }
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPass() {
        return this.pass;
    }
}
