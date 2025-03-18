package com.example.artisanconnect.controller;

import com.example.artisanconnect.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class CheckoutController {
    @FXML private TextField fullNameField;
    @FXML private TextField addressField;
    @FXML private TextField cardNumberField;
    @FXML private PasswordField passwordField;

    private ShoppingCart cart;
    private final String CLIENT_PASSWORD = "client123";

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    @FXML
    private void handlePayment() {
        if (!validateForm()) return;

        if (passwordField.getText().trim().equals(CLIENT_PASSWORD)) {
            Order newOrder = new Order(
                    "CMD-" + System.currentTimeMillis(),
                    cart.getItems(),
                    cart.getTotalPrice(),
                    OrderStatus.EN_PREPARATION
            );
            OrderTracker.getInstance().addOrder(newOrder);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/artisanconnect/view/OrderConfirmation.fxml"));
                Parent root = loader.load();
                OrderConfirmationController controller = loader.getController();
                controller.loadOrder(newOrder.getOrderId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 800, 600));
                stage.setTitle("Suivi de commande");
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible de charger le suivi.");
            }

            cart.clearCart();
        } else {
            showAlert("Accès refusé", "Mot de passe client incorrect");
        }
    }

    private boolean validateForm() {
        String fullName = fullNameField.getText().trim();
        String address = addressField.getText().trim();
        String cardNumber = cardNumberField.getText().trim();

        if (fullName.isEmpty() || address.isEmpty() || cardNumber.isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs.");
            return false;
        }

        if (!cardNumber.matches("\\d{16}")) {
            showAlert("Carte invalide", "Le numéro de carte doit contenir 16 chiffres.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}