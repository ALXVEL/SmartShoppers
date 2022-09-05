package com.example.smartshoppers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CustomerController {

    @FXML
    public TableView customerTable;

    @FXML
    public TableView shoppingList;

    Customer customer;
    ArrayList<User> userList = new ArrayList<User>();
    HashMap<Item,Store> itemStoreHashMap = new HashMap<Item,Store>();

    @FXML
    public CheckBox nearMe;

    @FXML
    public TextField kmText;

    @FXML
    public ListView savedLocation;

    @FXML
    public CheckBox onSale;

    @FXML
    public Label customerLabel;


    int switch2 = 1;
    int switch3 = 1;

    public void checkBoxListener(ActionEvent event) throws IOException{
        if(nearMe.isSelected()){
            kmText.setDisable(false);
        }else{
            kmText.setDisable(true);
        }
    }

    public void logoutButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.getUserList(userList);

        //change scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void getUserList(ArrayList<User> list){
        userList = list;
    }

    public void refreshButton(ActionEvent event) throws IOException{
        customerTable.getItems().clear();

        //Name Column
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Category Column
        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //Price Column
        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity Column
        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        if(switch2 == 1){
            customerTable.getColumns().addAll(nameColumn, categoryColumn, priceColumn, quantityColumn);
            switch2 = 0;
        }

        int distance = 0;
        getStoreList();
        if(nearMe.isSelected() && onSale.isSelected()){
            distance = Integer.parseInt(kmText.getText());
            for(Item i: itemStoreHashMap.keySet()){
                if(itemStoreHashMap.get(i).getDistance() <= distance && i.isOnSale()){
                    customerTable.getItems().add(i);
                }
            }
        }else if(nearMe.isSelected() && !onSale.isSelected()){
            distance = Integer.parseInt(kmText.getText());
            for(Item i: itemStoreHashMap.keySet()){
                if(itemStoreHashMap.get(i).getDistance() <= distance){
                    customerTable.getItems().add(i);
                }
            }
        }else if (!nearMe.isSelected() && onSale.isSelected()){
            for(Item i: itemStoreHashMap.keySet()){
                if(i.isOnSale()){
                    customerTable.getItems().add(i);
                }
            }
        }else{
            for(Item i: itemStoreHashMap.keySet()){
                customerTable.getItems().add(i);
            }
        }


    }

    public void getStoreList(){
        HashSet<Store> storeList = new HashSet<Store>();
        System.out.println("User List: " + userList);
        for(User u: userList){
            if(u instanceof Admin){
                for(Store s: ((Admin) u).storeList){
                    storeList.add(s);
                }
            }
        }

        for(Store s: storeList){
            for(Item i : s.getItemList()){
                itemStoreHashMap.put(i,s);
            }
        }

        System.out.println("Store list: " + storeList);
        System.out.println("Map: " + itemStoreHashMap);
    }

    public void addToShoppingList(ActionEvent event) throws IOException{
        ObservableList<Item> itemSelected;
        itemSelected = customerTable.getSelectionModel().getSelectedItems();


        if(switch3 == 1){
            //Name Column
            TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //Category Column
            TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

            //Price Column
            TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            //Quantity Column
            TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            //on Sale column
            TableColumn<Item, Boolean> saleColumn = new TableColumn<>("Sale");
            saleColumn.setCellValueFactory(new PropertyValueFactory<>("onSale"));

            //Store
            TableColumn<Store, String> storeColumn = new TableColumn<>("Store");
            storeColumn.setCellValueFactory(new PropertyValueFactory<>("store"));

            shoppingList.getColumns().addAll(nameColumn, categoryColumn, priceColumn, quantityColumn, saleColumn, storeColumn);
            switch3 = 0;
        }
        customer.addToShoppingList(itemSelected.get(0));
        shoppingList.getItems().clear();
        for(Item i: customer.shoppingList){
            shoppingList.getItems().add(i);
        }

        System.out.println(customer.getShoppingList());

    }

    public void removeFromShoppingList(ActionEvent event) throws IOException{
        ObservableList<Item> itemSelected;
        itemSelected = shoppingList.getSelectionModel().getSelectedItems();
        System.out.println("Before removal: " + customer.getShoppingList());
        //shoppingList.getItems().remove(itemSelected.get(0));
        customer.getShoppingList().remove(itemSelected.get(0));
        shoppingList.getItems().clear();
        for(Item i: customer.getShoppingList()){
            shoppingList.getItems().add(i);
        }
        System.out.println("After removal: " + customer.getShoppingList());
    }

    public void saveLocation(ActionEvent event) throws IOException{
        ObservableList<Item> itemSelected;
        itemSelected = customerTable.getSelectionModel().getSelectedItems();

        customer.addToSavedLocations(itemStoreHashMap.get(itemSelected.get(0)));
        savedLocation.getItems().clear();
        for(Store s: customer.getSavedLocations()){
            savedLocation.getItems().add(s.toString());
        }
    }

    public void removeLocation(ActionEvent event) throws IOException{
        ObservableList<String> itemSelected;
        itemSelected = savedLocation.getSelectionModel().getSelectedItems();

        String[] split = itemSelected.get(0).split(":");

        customer.removeFromSavedLocation(split[0]);
        savedLocation.getItems().clear();
        for(Store s: customer.getSavedLocations()){
            savedLocation.getItems().add(s.toString());
        }
    }

    public void getCurrentCustomer(User a){
        customer = (Customer) a;
    }

    public void createShoppingColumns(){
        if(switch3 == 1){
            //Name Column
            TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //Category Column
            TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

            //Price Column
            TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            //Quantity Column
            TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            //on Sale column
            TableColumn<Item, Boolean> saleColumn = new TableColumn<>("Sale");
            saleColumn.setCellValueFactory(new PropertyValueFactory<>("onSale"));

            //Store
            TableColumn<Store, String> storeColumn = new TableColumn<>("Store");
            storeColumn.setCellValueFactory(new PropertyValueFactory<>("store"));

            shoppingList.getColumns().addAll(nameColumn, categoryColumn, priceColumn, quantityColumn, saleColumn, storeColumn);
            switch3 = 0;
        }
    }
}
