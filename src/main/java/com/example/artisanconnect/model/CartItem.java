package com.example.artisanconnect.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CartItem {
    private final StringProperty productId;
    private final StringProperty name;
    private final StringProperty imageUrl;
    private final DoubleProperty price;
    private final IntegerProperty quantity;
    private final StringProperty artisanName;
    private final StringProperty category;

    public CartItem(String productId, String name, String imageUrl, double price, int quantity, String artisanName, String category) {
        this.productId = new SimpleStringProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.imageUrl = new SimpleStringProperty(imageUrl);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.artisanName = new SimpleStringProperty(artisanName);
        this.category = new SimpleStringProperty(category);
    }

    // Getters et propriétés
    public String getProductId() { return productId.get(); }
    public StringProperty productIdProperty() { return productId; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getImageUrl() { return imageUrl.get(); }
    public StringProperty imageUrlProperty() { return imageUrl; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }

    public String getArtisanName() { return artisanName.get(); }
    public StringProperty artisanNameProperty() { return artisanName; }

    public String getCategory() { return category.get(); }
    public StringProperty categoryProperty() { return category; }

    public double getTotalPrice() { return getPrice() * getQuantity(); }
}