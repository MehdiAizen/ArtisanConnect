package com.example.artisanconnect.controller;

import com.example.artisanconnect.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private ToggleGroup roleGroup;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        if (roleGroup.getSelectedToggle() == null) {
            showAlert("Erreur", "Sélectionnez un rôle !");
            return;
        }

        String role = roleGroup.getSelectedToggle().getUserData().toString();
        String password = passwordField.getText().trim();

        try {
            if (role.equals("CLIENT") && password.equals("client123")) {
                redirectToClientInterface();
            }
            else if (role.equals("ARTISAN") && password.equals("artisan123")) {
                redirectToArtisanInterface();
            }
            else {
                showAlert("Erreur", "Mot de passe incorrect !");
            }
        } catch (IOException e) {
            showAlert("Erreur Critique", "Fichier introuvable : " + e.getMessage());
        }
    }

    private void redirectToClientInterface() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/artisanconnect/view/cart.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
    }

    private void redirectToArtisanInterface() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/artisanconnect/view/OrderTracking.fxml"));
        Parent root = loader.load();

        OrderTrackingController controller = loader.getController();
        // CORRECTION ICI : Ajout du deuxième paramètre
        controller.loadOrder("CMD-TEST", false); // false = mode artisan

        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}