package com.example.smartshoppers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void addToShoppingList() {
        Customer c = new Customer("customer", "customer");
        Store nStore = new Store("Walmart");
        Item i = new Item("Item", 123, 123, "Health", nStore);

        c.addToShoppingList(i);
        assertEquals(true, c.getShoppingList().contains(i));
    }

    @Test
    void getShoppingList() {
        Customer c = new Customer("customer", "customer");
        Store nStore = new Store("Walmart");

        Item i1 = new Item("Item1", 123,123,"Health", nStore);
        Item i2 = new Item("Item2", 123,123,"Health", nStore);
        Item i3 = new Item("Item3", 123,123,"Health", nStore);
        Item i4 = new Item("Item4", 123,123,"Health", nStore);

        c.addToShoppingList(i1);
        c.addToShoppingList(i2);
        c.addToShoppingList(i3);
        c.addToShoppingList(i4);

        assertEquals(4, c.getShoppingList().size());
    }

    @Test
    void addToSavedLocations() {
        Customer c = new Customer("customer", "customer");
        Store nStore = new Store("Walmart");

        c.addToSavedLocations(nStore);
        assertEquals(1, c.getSavedLocations().size());
    }

    @Test
    void getSavedLocations() {
        Customer c = new Customer("customer", "customer");
        Store nStore = new Store("Walmart");

        c.addToSavedLocations(nStore);
        assertEquals(1, c.getSavedLocations().size());

    }

    @Test
    void removeFromSavedLocation() {
        Customer c = new Customer("customer", "customer");
        Store nStore = new Store("Walmart");

        c.addToSavedLocations(nStore);
        c.addToSavedLocations(new Store("Adidas"));
        c.removeFromSavedLocation("Walmart");
        assertEquals(1, c.getSavedLocations().size());
    }
}