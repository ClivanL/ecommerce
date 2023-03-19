package com.example.main;


import com.example.main.models.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Main {

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

    public Main() {
    }

    public Main(String username, String name, String email, List<Cart> cartItems) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.cartItems = cartItems;
    }
}


