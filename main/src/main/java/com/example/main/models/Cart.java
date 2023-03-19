package com.example.main.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("purchased")
    private boolean purchased;

    public Cart() {
    }

    public Cart(Long itemId, int quantity, boolean purchased) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.purchased = purchased;
    }

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
