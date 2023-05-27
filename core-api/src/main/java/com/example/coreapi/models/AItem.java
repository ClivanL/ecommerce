package com.example.coreapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

;

public class AItem {

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

    @JsonProperty("rating")
    private float rating=0;

    @JsonProperty("hits")
    private int hits=0;

    @JsonProperty("likes")
    private int likes=0;


    public AItem(Long id, String itemName, Double price, String description, String imageUrl, int quantity, String category, Long ownerId) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.ownerId=ownerId;
    }

    public AItem(String itemName, Double price, String description, String imageUrl, int quantity, String category, Long ownerId) {
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
        this.ownerId = ownerId;
    }

    public AItem(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public AItem() {
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

    public float getRating() {
        return rating;
    }

    public int getHits() {
        return hits;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "AItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", ownerId=" + ownerId +
                ", rating=" + rating +
                ", hits=" + hits +
                '}';
    }
}