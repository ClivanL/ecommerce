package com.example.main.models;

import javax.persistence.Entity;

@Entity
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
