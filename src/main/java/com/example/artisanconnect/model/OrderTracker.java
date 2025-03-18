package com.example.artisanconnect.model;

import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

public class OrderTracker {
    private static OrderTracker instance;
    private final List<Order> orders = new ArrayList<>();

    private OrderTracker() {
        Order testOrder = new Order(
                "CMD-TEST",
                FXCollections.observableArrayList(),
                0.0,
                OrderStatus.EN_PREPARATION
        );
        orders.add(testOrder);
    }

    public static OrderTracker getInstance() {
        if (instance == null) {
            instance = new OrderTracker();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("Nouvelle commande ajoutée: " + order.getOrderId());
    }

    public Order findOrder(String orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}