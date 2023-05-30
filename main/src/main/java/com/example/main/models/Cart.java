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

    @JsonProperty("sufficient")
    private Boolean sufficient=true;

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
    public Cart(Long userId, Long itemId, int quantity, Long id) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.id=id;
    }

    public Cart(Long id, int quantity, Item item, Long itemId, Long userId) {
        this.id = id;
        this.quantity = quantity;
        this.item = item;
        this.itemId=itemId;
        this.userId=userId;
    }

    public Cart(Long id, int quantity, Item item, Boolean sufficient, Long itemId, Long userId) {
        this.id = id;
        this.quantity = quantity;
        this.item = item;
        this.sufficient = sufficient;
        this.itemId=itemId;
        this.userId=userId;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
