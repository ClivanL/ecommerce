package com.example.ecommerce.purchaseLog;

import java.io.Serializable;

public class PurchaseLogDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long itemId;

    public PurchaseLogDTO(Long id, Long userId, Long itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }

    public PurchaseLogDTO(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public PurchaseLogDTO() {
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
        return "PurchaseLogDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                '}';
    }
}
