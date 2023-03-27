package com.example.main.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cart {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("item")
    private Item item;

    public Cart() {
    }

    public Cart(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public Cart(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Cart(Long userId, Long itemId, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
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



}
