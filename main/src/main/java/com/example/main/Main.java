package com.example.main;


import com.example.main.models.Cart;
import com.example.main.models.Favourite;
import com.example.main.models.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Main {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;
//    private Long itemId;
//    private int quantity;
//    private boolean purchased;
    @JsonProperty("carts")
    private List<Cart> cartItems;

    @JsonProperty("listedItems")
    private List<Item> listedItems;

    @JsonProperty("fulfillableCart")
    private Boolean fulfillableCart=true;

    @JsonProperty("favourites")
    private List<Favourite> favourites;

    @JsonProperty("balance")
    private float balance;

    public Main() {
    }

    public Main(String username, String name, String email, List<Cart> cartItems) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.cartItems = cartItems;
    }

    public Main(Long userId, String username, String name, String email, List<Cart> cartItems, List<Item> listedItems) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.cartItems = cartItems;
        this.listedItems=listedItems;
    }

    public Main(Long userId, String username, String name, String email, List<Cart> cartItems, List<Item> listedItems, Boolean fulfillableCart, List<Favourite> favourites, float balance) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.cartItems = cartItems;
        this.listedItems = listedItems;
        this.fulfillableCart = fulfillableCart;
        this.favourites=favourites;
        this.balance=balance;
    }

    public Main(Main that) {
        this(that.getUserId(),that.getUsername(),that.getName(),that.getEmail(),that.getCartItems(),that.getListedItems());
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getListedItems() {
        return listedItems;
    }

    public void setListedItems(List<Item> listedItems) {
        this.listedItems = listedItems;
    }

    @Override
    public String toString() {
        return "Main{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", cartItems=" + cartItems +
                ", listedItems=" + listedItems +
                ", fulfillableCart=" + fulfillableCart +
                ", favourites=" + favourites +
                ", balance=" + balance +
                '}';
    }
}


