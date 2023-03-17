package com.example.purchaseLog;


import javax.persistence.*;

@Entity
@Table(name="purchaselog", schema="public")
public class PurchaseLog {
    @Id
    @SequenceGenerator(
            name="purchaselog_sequence",
            sequenceName = "purchaselog_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="purchaselog_sequence"
    )
    private Long id;
    private Long itemId;
    private Long userId;

    public PurchaseLog(Long id, Long itemId, Long userId) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog(Long itemId, Long userId) {
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + userId +
                '}';
    }
}
