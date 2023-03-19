package com.example.main.models;


public class Cart {
    private Long id;
    private Long userId;
    private Long itemId;
    private int quantity;
    private boolean purchased;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }
}
