package com.example.main;

import javax.persistence.Entity;

@Entity
public class Main {

    private String username;
    private String name;
    private String email;
    private Long itemId;
    private int quantity;
    private boolean purchased;

    public Main() {
    }

    public Main(String username, String name, String email, Long itemId, int quantity, boolean purchased) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.itemId = itemId;
        this.quantity = quantity;
        this.purchased = purchased;
    }
}


