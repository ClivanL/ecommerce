package com.example.ecommerce.purchaseLog;


import com.example.ecommerce.item.Item;
import com.example.ecommerce.user.User;

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

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public PurchaseLog() {
    }

    public PurchaseLog(Long id, Item item, User user) {
        this.id = id;
        this.item = item;
        this.user = user;
    }

    public PurchaseLog(Item item, User user) {
        this.item = item;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "id=" + id +
                ", item=" + item +
                ", user=" + user +
                '}';
    }
}
