package com.example.smartshoppers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpController {

    private Scene scene;
    private Parent root;

    User u;
    ArrayList<User> userArrayList = new ArrayList<User>();

    @FXML
    private TextField myUsername;
    @FXML
    private TextField myPassword;
    @FXML
    private CheckBox customerCheck;
    @FXML
    private CheckBox adminCheck;
    @FXML
    private CheckBox managerCheck;

    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();
        Controller controller = loader.getController();
        controller.getUserList(userArrayList);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void createNewUser(ActionEvent event) throws IOException {
        if(customerCheck.isSelected()){
            u = new Customer(myUsername.getText(), myPassword.getText());
        }else if(adminCheck.isSelected()){
            u = new Admin(myUsername.getText(), myPassword.getText());
        }else if(managerCheck.isSelected()){
            u = new Manager(myUsername.getText(), myPassword.getText());
        }
        userArrayList.add(u);
        System.out.println(userArrayList);
        //pass the data
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();
        Controller controller = loader.getController();
        controller.getUserList(userArrayList);

        //change scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    //method is being used by controller class
    public void getUserList(ArrayList<User> list){
        userArrayList = list;
    }
}
