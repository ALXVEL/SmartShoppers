package com.example.smartshoppers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;


public class AdminController {
    @FXML
    public ChoiceBox storeChoice;

    @FXML
    public TableView itemTable;

    @FXML
    public ListView managerList;

    @FXML
    public Label adminLabel;

    int switch2 = 1;

    Admin admin;
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<Store> storeList = new ArrayList<Store>();

    public void removeStoreButton(ActionEvent event) throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Confirm Removal");
        window.setMinWidth(250);

        Label nLabel = new Label("Are you sure you want to remove: " + storeChoice.getSelectionModel().getSelectedItem());
        int index = storeChoice.getSelectionModel().getSelectedIndex();
        String nameOfStore = storeChoice.getSelectionModel().getSelectedItem().toString();
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction((ActionEvent) -> {
            itemTable.getItems().clear();
            itemTable.getColumns().clear();
            switch2 = 1;
            storeChoice.getItems().remove(index);
            admin.removeFromStoreList(nameOfStore);
            window.close();
        });
        Button declineButton = new Button("Decline");
        declineButton.setOnAction((ActionEvent) -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nLabel, confirmButton, declineButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void addStoreButton(ActionEvent event) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Add a Store");
        window.setMinWidth(250);

        TextField tf = new TextField();
        Button addButton = new Button("Add Store");
        addButton.setOnAction((ActionEvent) -> {
            Store s = new Store(tf.getText());
            storeList.add(s);
            admin.addToStoreList(s);
            storeChoice.getItems().add(tf.getText());
            System.out.println(admin + " added store.");
        });
        Button closeButton = new Button("Close");
        closeButton.setOnAction((ActionEvent) -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tf, addButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //shows window and waits till it is closed
    }

    public void getCurrentAdmin(User a){
        admin = (Admin) a;
    }

    public void choiceBoxListener(ActionEvent event) throws IOException{
        System.out.println("Testing");
        itemTable.getItems().clear();
        managerList.getItems().clear();


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

        if(switch2 == 1){
            itemTable.getColumns().addAll(nameColumn, categoryColumn, priceColumn, quantityColumn, saleColumn, storeColumn);
            switch2 = 0;
        }

        if(storeChoice.getSelectionModel().getSelectedItem() != null){

            Store currentStore = admin.getStore(storeChoice.getSelectionModel().getSelectedItem().toString());

            //testing (to be removed)
//            currentStore.addItemToList(new Item("b", 12, 21, "t"));
//            currentStore.addItemToList(new Item("c", 155, 2453451, "t"));
//            currentStore.addItemToList(new Item("d", 232, 2541, "d"));
//            currentStore.addItemToList(new Item("a", 1562, 234531, "v"));

            for (Item i: currentStore.itemList) {
                itemTable.getItems().add(i);
            }

            for(User u: currentStore.managerList){
                managerList.getItems().add(u.getUsername());
            }
        }


    }

    public void addItemButton(ActionEvent event) throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Add an Item");
        window.setMinWidth(250);

        Store currentStore = admin.getStore(storeChoice.getSelectionModel().getSelectedItem().toString());

        HBox layout = new HBox(20);
        CheckBox onSale = new CheckBox("On Sale");
        TextField itemName = new TextField("Name");
        TextField itemQuantity = new TextField("Quantity");
        TextField itemPrice = new TextField("Price");
        TextField itemCategory = new TextField("Category");
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction((ActionEvent) -> {
            String name = itemName.getText();
            String category = itemCategory.getText();
            int quantity = Integer.parseInt(itemQuantity.getText());
            int price = Integer.parseInt(itemPrice.getText());
            Item nItem = new Item(name, price, quantity, category, currentStore);
            if(onSale.isSelected()){
                nItem.setSale(true);
            }else{
                nItem.setSale(false);
            }
            currentStore.addItemToList(nItem);
            itemTable.getItems().add(nItem);
            window.close();
        });
        Button backButton = new Button("Back");
        backButton.setOnAction((ActionEvent) -> {
            window.close();
        });

        layout.getChildren().addAll(itemName, itemPrice, itemQuantity, itemCategory, onSale, confirmButton, backButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void modifyItemButton(ActionEvent event) throws IOException{
        ObservableList<Item> itemSelected, allItems;
        allItems = itemTable.getItems();
        itemSelected = itemTable.getSelectionModel().getSelectedItems();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Modify Item");
        window.setMinWidth(250);

        TextField tf_name = new TextField(itemSelected.get(0).getName());
        TextField tf_price = new TextField(Double.toString(itemSelected.get(0).getPrice()));
        TextField tf_quantity = new TextField(Integer.toString(itemSelected.get(0).getQuantity()));
        TextField tf_category = new TextField(itemSelected.get(0).getCategory());
        CheckBox onSale = new CheckBox("on Sale");
        Button modify = new Button("Modify");
        modify.setOnAction((ActionEvent) -> {
            Item i = itemSelected.get(0);
            itemTable.getItems().remove(itemSelected.get(0));
            i.setCategory(tf_category.getText());
            i.setName(tf_name.getText());
            i.setPrice(Double.parseDouble(tf_price.getText()));
            i.setQuantity(Integer.parseInt(tf_quantity.getText()));
            if(onSale.isSelected()){
                i.setSale(true);
            }else{
                i.setSale(false);
            }
            itemTable.getItems().add(i);
            itemTable.refresh();
            window.close();
        });
        Button remove = new Button("Remove");
        Store currentStore = admin.getStore(storeChoice.getSelectionModel().getSelectedItem().toString());
        remove.setOnAction((ActionEvent) -> {
            currentStore.removeItemFromList(itemSelected.get(0));
            itemSelected.forEach(allItems::remove);
            window.close();
        });
        Button back = new Button("Back");
        back.setOnAction((ActionEvent) ->{
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tf_name, tf_price, tf_quantity, tf_category, onSale, modify, remove, back);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        System.out.println(itemSelected);
    }

    public void addManagerButton(ActionEvent event) throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Add a Manager");
        window.setMinWidth(250);

        Store currentStore = admin.getStore(storeChoice.getSelectionModel().getSelectedItem().toString());

        Label nLabel = new Label("Enter Manager:");
        TextField managerName = new TextField();
        Button confirmButton = new Button("Confirm");
        System.out.println(userList);

        confirmButton.setOnAction((ActionEvent) -> {
            Iterator<User> iterator = userList.iterator();
            while(iterator.hasNext()){
                User u = iterator.next();
                if(u.getUsername().equals(managerName.getText()) && u instanceof Manager){
                    managerList.getItems().add(managerName.getText());
                    currentStore.addManager(u);
                    Manager m = (Manager) u;
                    m.addToStoreList(currentStore);
                }
            }
            window.close();
        });
        Button backButton = new Button("Cancel");
        backButton.setOnAction((ActionEvent) -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nLabel, managerName, confirmButton, backButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        if(currentStore != null){
            window.showAndWait();
        }

    }

    public void removeManagerButton(ActionEvent event) throws IOException{
        String managerName = managerList.getSelectionModel().getSelectedItem().toString();
        managerList.getItems().remove(managerList.getSelectionModel().getSelectedItem());

        Store currentStore = admin.getStore(storeChoice.getSelectionModel().getSelectedItem().toString());

        User removeThis = null;
        Iterator<User> iterator = currentStore.managerList.iterator();
        while(iterator.hasNext()){
            User u = iterator.next();
            if(u.getUsername().equals(managerName)){
                removeThis = u;
            }
        }
        Manager m = (Manager) removeThis;
        m.removeFromStoreList(currentStore.getName());
        currentStore.removeManager(removeThis);
        System.out.println(currentStore.managerList);
    }
    
    public void logoutButton(ActionEvent event) throws IOException{
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

    public ChoiceBox getChoiceBox(){
        return storeChoice;
    }

    public ArrayList<Store> getStoreList(){
        return storeList;
    }

}
