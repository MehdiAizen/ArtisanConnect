package com.example.artisanconnect.controller;

import com.example.artisanconnect.model.CartItem;
import com.example.artisanconnect.model.ShoppingCart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;

public class CartController implements Initializable {
    @FXML private TableView<CartItem> cartTableView;
    @FXML private TableColumn<CartItem, String> nameColumn;
    @FXML private TableColumn<CartItem, String> artisanColumn;
    @FXML private TableColumn<CartItem, Double> priceColumn;
    @FXML private TableColumn<CartItem, Integer> quantityColumn;
    @FXML private TableColumn<CartItem, Double> totalColumn;
    @FXML private TableColumn<CartItem, Void> actionColumn;
    @FXML private Label totalPriceLabel;
    @FXML private Button checkoutButton;
    @FXML private Button clearCartButton;
    @FXML private VBox emptyCartBox;
    @FXML private Button continueShoppingButton;
    @FXML private ImageView emptyCartImageView;

    private final ShoppingCart cart = new ShoppingCart();
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(
            new Locale.Builder().setLanguage("fr").setRegion("TN").build()
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Chargement de l'image du panier vide
        try {
            Image emptyImage = new Image(getClass().getResourceAsStream("/com/example/artisanconnect/images/empty_cart.png"));
            emptyCartImageView.setImage(emptyImage);
        } catch (NullPointerException e) {
            System.err.println("ERREUR: Image non trouvée ! Vérifiez le chemin.");
        }

        // Configuration des colonnes du tableau
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        artisanColumn.setCellValueFactory(new PropertyValueFactory<>("artisanName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalColumn.setCellValueFactory(cellData ->
                cellData.getValue().priceProperty().multiply(cellData.getValue().quantityProperty()).asObject()
        );

        // Configuration des cellules
        setupQuantityColumn();
        setupActionColumn();

        // Liaison des données au tableau
        cartTableView.setItems(cart.getItems());
        cart.totalPriceProperty().addListener((obs, oldVal, newVal) ->
                totalPriceLabel.setText(currencyFormat.format(newVal))
        );

        // Gestion des boutons
        checkoutButton.setOnAction(e -> proceedToCheckout());
        clearCartButton.setOnAction(e -> cart.clearCart());
        continueShoppingButton.setOnAction(e -> showProductListing());

        // Mise à jour de l'état du panier
        updateCartEmptyState();
        cart.getItems().addListener((ListChangeListener.Change<? extends CartItem> change) ->
                updateCartEmptyState()
        );

        // Ajout de données de test
        addSampleItems();
    }

    private void setupQuantityColumn() {
        quantityColumn.setCellFactory(param -> new TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(1, 100, 1);

            {
                spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cart.updateItemQuantity(item, newVal);
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().isEmpty()) {
                    setGraphic(null);
                } else {
                    CartItem currentItem = getTableView().getItems().get(getIndex());
                    spinner.getValueFactory().setValue(currentItem.getQuantity());
                    setGraphic(spinner);
                }
            }
        });
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeBtn = new Button("✕");

            {
                removeBtn.getStyleClass().add("remove-button");
                removeBtn.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    cart.removeItem(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeBtn);
            }
        });
    }

    private void proceedToCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/artisanconnect/view/Checkout.fxml"));
            Parent root = loader.load();
            CheckoutController controller = loader.getController();
            controller.setCart(cart);

            Stage stage = (Stage) checkoutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("ArtisanConnect - Paiement");
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la page de paiement.");
        }
    }

    private void showProductListing() {
        // Navigation vers la liste des produits
    }

    private void updateCartEmptyState() {
        boolean empty = cart.getItems().isEmpty();
        cartTableView.setVisible(!empty);
        emptyCartBox.setVisible(empty);
        checkoutButton.setDisable(empty);
        clearCartButton.setDisable(empty);
    }

    private void addSampleItems() {
        cart.addItem(new CartItem(
                "P001",
                "Vase en céramique",
                "images/vase.jpg",
                45.0,
                1,
                "Mohamed Salah",
                "Poterie"
        ));
        cart.addItem(new CartItem(
                "P002",
                "Tapis traditionnel",
                "images/tapis.jpg",
                120.0,
                1,
                "Fatima Ben Ali",
                "Tissage"
        ));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
