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

public class ManagerController {

    @FXML
    public ChoiceBox managerChoiceBox;

    @FXML
    public TableView managerTable;

    @FXML
    public Label managerLabel;

    Manager manager;

    ArrayList<User> userList = new ArrayList<User>();
    int switch2 = 1;

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

    public void choiceBoxListener(ActionEvent event) throws IOException{
        System.out.println("Testing");
        managerTable.getItems().clear();

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
            managerTable.getColumns().addAll(nameColumn, categoryColumn, priceColumn, quantityColumn, saleColumn, storeColumn);
            switch2 = 0;
        }

        if(managerChoiceBox.getSelectionModel().getSelectedItem() != null){

            Store currentStore = manager.getStore(managerChoiceBox.getSelectionModel().getSelectedItem().toString());
            //testing (to be removed)
//            currentStore.addItemToList(new Item("b", 12, 21, "t"));
//            currentStore.addItemToList(new Item("c", 155, 2453451, "t"));
//            currentStore.addItemToList(new Item("d", 232, 2541, "d"));
//            currentStore.addItemToList(new Item("a", 1562, 234531, "v"));

            for (Item i: currentStore.itemList) {
                managerTable.getItems().add(i);
            }
        }
    }

    public void getUserList(ArrayList<User> list){
        userList = list;
    }

    public void getCurrentManager(User m){
        manager = (Manager) m;
    }

    public void modifyItemButton(ActionEvent event) throws IOException{
        ObservableList<Item> itemSelected, allItems;
        allItems = managerTable.getItems();
        itemSelected = managerTable.getSelectionModel().getSelectedItems();

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Modify Item");
        window.setMinWidth(250);

        TextField tf_name = new TextField(itemSelected.get(0).getName());
        TextField tf_price = new TextField(Double.toString(itemSelected.get(0).getPrice()));
        TextField tf_quantity = new TextField(Integer.toString(itemSelected.get(0).getQuantity()));
        TextField tf_category = new TextField(itemSelected.get(0).getCategory());
        CheckBox onSale = new CheckBox("on Sale");
        if(itemSelected.get(0).isOnSale()){
            onSale.setSelected(true);
        }else{
            onSale.setSelected(false);
        }
        Button modify = new Button("Modify");
        modify.setOnAction((ActionEvent) -> {
            Item i = itemSelected.get(0);
            managerTable.getItems().remove(itemSelected.get(0));
            i.setCategory(tf_category.getText());
            i.setName(tf_name.getText());
            i.setPrice(Double.parseDouble(tf_price.getText()));
            i.setQuantity(Integer.parseInt(tf_quantity.getText()));
            if(onSale.isSelected()){
                i.setSale(true);
            }else{
                i.setSale(false);
            }
            managerTable.getItems().add(i);
            managerTable.refresh();
            window.close();
        });
        Button remove = new Button("Remove");
        Store currentStore = manager.getStore(managerChoiceBox.getSelectionModel().getSelectedItem().toString());
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

    public void addItemButton(ActionEvent event) throws IOException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //blocks user action until window complete
        window.setTitle("Add an Item");
        window.setMinWidth(250);

        Store currentStore = manager.getStore(managerChoiceBox.getSelectionModel().getSelectedItem().toString());

        HBox layout = new HBox(20);
        TextField itemName = new TextField("Name");
        TextField itemQuantity = new TextField("Quantity");
        TextField itemPrice = new TextField("Price");
        TextField itemCategory = new TextField("Category");
        Button confirmButton = new Button("Confirm");
        CheckBox onSale = new CheckBox("on Sale");
        confirmButton.setOnAction((ActionEvent) -> {
            String name = itemName.getText();
            String category = itemCategory.getText();
            int quantity = Integer.parseInt(itemQuantity.getText());
            int price = Integer.parseInt(itemPrice.getText());
            Item nItem = new Item(name, price, quantity, category,currentStore);
            if(onSale.isSelected()){
                nItem.setSale(true);
            }else{
                nItem.setSale(false);
            }
            currentStore.addItemToList(nItem);
            managerTable.getItems().add(nItem);
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
}
