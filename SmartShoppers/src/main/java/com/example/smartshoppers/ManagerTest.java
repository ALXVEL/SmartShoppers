package com.example.smartshoppers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void removeFromStoreList() {
        Manager m  = new Manager("manager", "manager");
        Store nStore = new Store("Walmart");
        m.addToStoreList(nStore);
        m.removeFromStoreList("Walamrt");
        assertEquals(0, m.storeList.size());
    }

    @Test
    void getStore() {
        Manager m  = new Manager("manager", "manager");
        Store nStore = new Store("Walmart");
        m.addToStoreList(nStore);
        assertEquals(nStore, m.getStore("Walmart"));
    }
}