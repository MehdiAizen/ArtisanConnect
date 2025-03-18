package com.example.artisanconnect.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShoppingCart {
    private final ObservableList<CartItem> items = FXCollections.observableArrayList();
    private final DoubleProperty totalPrice = new SimpleDoubleProperty(0.0);

    public void addItem(CartItem item) {
        items.add(item);
        totalPrice.set(totalPrice.get() + item.getPrice() * item.getQuantity());
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        totalPrice.set(totalPrice.get() - item.getPrice() * item.getQuantity());
    }

    public void updateItemQuantity(CartItem item, int newQuantity) {
        totalPrice.set(totalPrice.get() + item.getPrice() * (newQuantity - item.getQuantity()));
        item.setQuantity(newQuantity);
    }

    public void clearCart() {
        items.clear();
        totalPrice.set(0.0);
    }

    // Getters
    public ObservableList<CartItem> getItems() { return items; }
    public DoubleProperty totalPriceProperty() { return totalPrice; }
    public double getTotalPrice() { return totalPrice.get(); }
}