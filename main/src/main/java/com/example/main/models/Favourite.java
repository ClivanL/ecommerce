package com.example.main.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Favourite {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("item")
    private Item item;

    public Favourite() {
    }

    public Favourite(Long userId, Long itemId, Item item) {
        this.userId = userId;
        this.itemId = itemId;
        this.item=item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
