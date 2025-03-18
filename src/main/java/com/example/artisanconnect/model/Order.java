package com.example.artisanconnect.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class Order {
    private final String orderId;
    private final ObservableList<CartItem> items;
    private final double total;
    private final ObjectProperty<OrderStatus> status;

    public Order(String orderId, ObservableList<CartItem> items, double total, OrderStatus status) {
        this.orderId = orderId;
        this.items = items;
        this.total = total;
        this.status = new SimpleObjectProperty<>(status);
    }

    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status.get(); }
    public ObjectProperty<OrderStatus> statusProperty() { return status; }
    public void setStatus(OrderStatus status) { this.status.set(status); }
    public ObservableList<CartItem> getItems() { return items; }
}