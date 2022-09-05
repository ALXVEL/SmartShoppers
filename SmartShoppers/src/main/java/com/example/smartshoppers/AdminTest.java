package com.example.smartshoppers;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @org.junit.jupiter.api.Test
    void addToStoreList() {
        Store nStore = new Store("Walmart");
        Admin nAdmin = new Admin("admin", "admin");

        nAdmin.addToStoreList(nStore);
        assertEquals(nStore, nAdmin.getStore("Walmart"));
    }

    @org.junit.jupiter.api.Test
    void removeFromStoreList() {
        Store nStore = new Store("Walmart");
        Admin nAdmin = new Admin("admin", "admin");

        nAdmin.addToStoreList(nStore);
        nAdmin.removeFromStoreList("Walmart");
        assertEquals(0, nAdmin.storeList.size());
    }

    @org.junit.jupiter.api.Test
    void getStore() {
        Store nStore = new Store("Walmart");
        Admin nAdmin = new Admin("admin", "admin");

        nAdmin.addToStoreList(nStore);
        assertEquals(nStore, nAdmin.getStore("Walmart"));
    }
}