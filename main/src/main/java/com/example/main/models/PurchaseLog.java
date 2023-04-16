package com.example.main.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PurchaseLog {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("item")
    private Item item;

    @JsonProperty("ownerUsername")
    private String ownerUsername;

    @JsonProperty("userUsername")
    private String userUsername;

    @JsonProperty("isSent")
    private Boolean isSent=false;

    @JsonProperty("isReceived")
    private Boolean isReceived=false;



    public PurchaseLog(Long id, Long itemId, Long userId) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog() {
    }

    public PurchaseLog(Long itemId, Long userId, int quantity, Long ownerId) {
        this.itemId = itemId;
        this.userId = userId;
        this.quantity=quantity;
        this.ownerId=ownerId;
    }

    public PurchaseLog(Long id, Long itemId, Long userId, int quantity, Long ownerId, LocalDateTime createdAt) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
        this.quantity = quantity;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public PurchaseLog(int quantity, LocalDateTime createdAt, Item item) {
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.item = item;
    }

    public PurchaseLog(int quantity, LocalDateTime createdAt, Item item, String ownerUsername, Boolean isSent, Boolean isReceived) {
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.item = item;
        this.ownerUsername = ownerUsername;
        this.isSent=isSent;
        this.isReceived=isReceived;
    }

    public PurchaseLog(Long userId, int quantity, LocalDateTime createdAt, Item item, String userUsername, Boolean isSent, Boolean isReceived) {
        this.userId = userId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.item = item;
        this.userUsername = userUsername;
        this.isSent=isSent;
        this.isReceived=isReceived;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Boolean getSent() {
        return isSent;
    }

    public Boolean getReceived() {
        return isReceived;
    }
}
