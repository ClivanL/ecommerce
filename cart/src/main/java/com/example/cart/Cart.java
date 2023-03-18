package com.example.cart;


import javax.persistence.*;

@Entity
@Table(name="cart", schema="public")
public class Cart {

    @Id
    @SequenceGenerator(
            name="item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private Long userId;
    private Long itemId;
    private int quantity;
    private boolean purchased;


    public Cart(Long id, Long userId, Long itemId, int quantity, boolean purchased) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.quantity=quantity;
        this.purchased=purchased;
    }

    public Cart(Long userId, Long itemId, int quantity, boolean purchased) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity=quantity;
        this.purchased=purchased;
    }

    public Cart() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", purchased=" + purchased +
                '}';
    }
}
