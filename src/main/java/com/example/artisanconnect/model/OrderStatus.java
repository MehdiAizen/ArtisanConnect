package com.example.artisanconnect.model;

public enum OrderStatus {
    EN_ATTENTE("En attente"),
    EN_PREPARATION("En préparation"),
    EXPEDIE("Expédié"),
    LIVRE("Livré");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}