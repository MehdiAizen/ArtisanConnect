package com.example.artisanconnect.controller;

import com.example.artisanconnect.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // Import ajouté ici
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class OrderConfirmationController {
    @FXML private Label orderIdLabel;
    @FXML private Label statusLabel;
    private Order trackedOrder;
    private String currentOrderId;

    public void loadOrder(String orderId) {
        this.currentOrderId = orderId;
        trackedOrder = OrderTracker.getInstance().findOrder(orderId);

        if (trackedOrder != null) {
            updateStatusDisplay(trackedOrder.getStatus());

            // Écouteur pour les mises à jour en temps réel
            trackedOrder.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                Platform.runLater(() -> updateStatusDisplay(newStatus));
            });
        }
    }

    private void updateStatusDisplay(OrderStatus status) {
        statusLabel.setText("Statut : " + status.toString());
        orderIdLabel.setText("Commande #" + currentOrderId);
    }

    @FXML
    private void handleReturnToHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/artisanconnect/view/Login.fxml"));
            Stage stage = (Stage) orderIdLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page d'accueil");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Fonctionne maintenant
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}