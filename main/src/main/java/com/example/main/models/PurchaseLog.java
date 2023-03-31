package com.example.main.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseLog {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("quantity")
    private int quantity;

    public PurchaseLog(Long id, Long itemId, Long userId) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog() {
    }

    public PurchaseLog(Long itemId, Long userId, int quantity) {
        this.itemId = itemId;
        this.userId = userId;
        this.quantity=quantity;
    }
}
