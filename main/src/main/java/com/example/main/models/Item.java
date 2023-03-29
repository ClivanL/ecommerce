package com.example.main.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("category")
    private String category;

    @JsonProperty("ownerId")
    private Long ownerId;

    public Item() {
    }

    public Item(String itemName, Double price, String description, String imageUrl, int quantity, String category, Long ownerId) {
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.ownerId = ownerId;
    }

    public Item(Long id, String itemName, Double price, String description, String imageUrl, int quantity, String category, Long ownerId) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.ownerId = ownerId;
    }

    public Item(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
