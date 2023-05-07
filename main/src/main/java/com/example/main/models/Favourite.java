package com.example.main.models;

public class Favourite {

    private Long id;
    private Long userId;
    private Long itemId;

    public Favourite() {
    }

    public Favourite(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
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
