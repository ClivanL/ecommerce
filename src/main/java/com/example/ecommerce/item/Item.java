package com.example.ecommerce.item;

import javax.persistence.*;

@Entity
@Table(name="item", schema="public")
public class Item {

    @Id
    @SequenceGenerator(
            name="item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="item_sequence"
    )
    private Long id;
    private String itemName;
    private Double price;
    private String description;
    private String imageUrl;
    private int quantity;

    public Item(Long id, String itemName, Double price, String description, String imageUrl, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Item(String itemName, Double price, String description, String imageUrl, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Item() {
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
