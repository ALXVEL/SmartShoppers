package com.example.smartshoppers;

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
import java.util.Iterator;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<User> userArrayList = new ArrayList<User>();

    @FXML
    public TextField user;
    @FXML
    public TextField pass;

    ChoiceBox cb = new ChoiceBox<>();
    TableView tb = new TableView<>();
    ListView clv = new ListView<>();

    public void signUpButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        root = loader.load();
        SignUpController controller = loader.getController();
        controller.getUserList(userArrayList);

       // Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void getUserList(ArrayList<User> list){
        userArrayList = list;
    }

    public void loginButton(ActionEvent event) throws IOException {
        for (User u: userArrayList) {
            if(u.getUsername().equals(user.getText())){
                if(u.getPass().equals(pass.getText())){
                    if(u instanceof Customer){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"));
                        root = loader.load();
                        CustomerController customer = loader.getController();
                        customer.getUserList(userArrayList);
                        customer.getCurrentCustomer(u);
                        customer.kmText.setDisable(true);
                        customer.createShoppingColumns();
                        tb = customer.shoppingList;
                        tb.getItems().clear();
                        for(Item i: ((Customer) u).getShoppingList()){
                            tb.getItems().add(i);
                        }
                        clv = customer.savedLocation;
                        clv.getItems().clear();
                        for(Store s: ((Customer)u).getSavedLocations()){
                            clv.getItems().add(s);
                        }
                        customer.customerLabel.setText(u.getUsername());
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                    }else if(u instanceof Manager){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("manager1.fxml"));
                        root = loader.load();
                        ManagerController managerController = loader.getController();
                        managerController.getCurrentManager(u);
                        managerController.getUserList(userArrayList);
                        managerController.managerLabel.setText(u.getUsername());
                        cb = managerController.managerChoiceBox;
                        Iterator<Store> iterator = ((Manager) u).storeList.iterator();
                        while(iterator.hasNext()){
                            Store s = iterator.next();
                            cb.getItems().add(s.getName());
                        }
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                    }else if(u instanceof Admin){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                        root = loader.load();
                        AdminController adminController = loader.getController();
                        adminController.getCurrentAdmin(u);
                        adminController.getUserList(userArrayList);
                        adminController.adminLabel.setText(u.getUsername());
                        cb = adminController.getChoiceBox();
                        Iterator<Store> iterator = ((Admin) u).storeList.iterator();
                        while(iterator.hasNext()){
                            Store s = iterator.next();
                            cb.getItems().add(s.getName());
                        }

                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                    }
                }
            }
        }

        System.out.println(userArrayList);
    }

}