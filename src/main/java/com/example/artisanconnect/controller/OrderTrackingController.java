package com.example.artisanconnect.controller;

import com.example.artisanconnect.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class OrderTrackingController {
    @FXML private Label orderIdLabel;
    @FXML private Label statusLabel;
    @FXML private ComboBox<OrderStatus> statusComboBox;
    @FXML private PasswordField artisanPasswordField;
    @FXML private HBox artisanControls;

    private final String ARTISAN_PASSWORD = "artisan123";
    private Order currentOrder;
    private boolean isClientView = false;

    public void initialize() {
        // Configuration initiale de la ComboBox
        statusComboBox.getItems().setAll(OrderStatus.values());

        statusComboBox.setCellFactory(lv -> new ListCell<OrderStatus>() {
            @Override
            protected void updateItem(OrderStatus item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.toString());
            }
        });

        statusComboBox.setButtonCell(new ListCell<OrderStatus>() {
            @Override
            protected void updateItem(OrderStatus item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.toString());
            }
        });
    }

    public void loadOrder(String orderId, boolean isClientView) {
        this.isClientView = isClientView;
        currentOrder = OrderTracker.getInstance().findOrder(orderId);

        if (currentOrder != null) {
            orderIdLabel.setText("Commande #" + currentOrder.getOrderId());
            statusLabel.setText("Statut : " + currentOrder.getStatus());
            statusComboBox.setValue(currentOrder.getStatus());

            // Mode client
            if(isClientView) {
                artisanControls.setVisible(false);
                statusComboBox.setDisable(true);
            }
        }
    }

    @FXML
    private void handleUpdateStatus() {
        if (artisanPasswordField.getText().equals(ARTISAN_PASSWORD)) {
            currentOrder.setStatus(statusComboBox.getValue());
            statusLabel.setText("Statut : " + currentOrder.getStatus());
            showAlert("Succès", "Statut mis à jour avec succès!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Mot de passe incorrect!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleReturnToHome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/artisanconnect/view/Login.fxml"));
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}