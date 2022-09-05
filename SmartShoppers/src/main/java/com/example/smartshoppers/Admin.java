package com.example.smartshoppers;

import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.Iterator;

public class Admin implements User{
    String id, pass;
    ArrayList<Store> storeList;

    public Admin(String id, String pass){
        this.id = id;
        this.pass = pass;
        storeList = new ArrayList<Store>();
    }

    public void addToStoreList(Store s){
        storeList.add(s);
    }

    public void removeFromStoreList(String storeName){
        Iterator<Store> iterator = storeList.iterator();
        while(iterator.hasNext()){
            Store s = iterator.next();
            if(s.getName().equals(storeName)){
                iterator.remove();
            }
        }
    }

    public Store getStore(String storeName){
        Iterator<Store> iterator = storeList.iterator();
        Store store = null;
        while(iterator.hasNext()){
            Store s = iterator.next();
            if(s.getName().equals(storeName)){
                store = s;
            }
        }
        return store;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                '}';
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
