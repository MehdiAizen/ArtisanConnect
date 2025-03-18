module com.example.artisanconnect {
    // Modules JavaFX obligatoires
    requires javafx.controls;
    requires javafx.fxml;



    // Configuration FXML
    opens com.example.artisanconnect.controller to javafx.fxml;
    exports com.example.artisanconnect.model;
    exports com.example.artisanconnect;
}