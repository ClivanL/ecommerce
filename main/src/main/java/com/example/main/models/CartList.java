package com.example.main.models;

import java.util.ArrayList;
import java.util.List;

public class CartList {
    private List<Cart> carts;

    public CartList(){
        carts= new ArrayList<>();
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
