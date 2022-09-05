module com.example.smartshoppers {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.testng;
    requires junit;
    requires org.junit.jupiter.api;


    opens com.example.smartshoppers to javafx.fxml;
    exports com.example.smartshoppers;
}