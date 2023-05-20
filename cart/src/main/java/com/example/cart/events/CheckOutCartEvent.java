package com.example.cart.events;

import com.example.cart.Cart;

import java.util.List;

public class CheckOutCartEvent {
    public final String cartId;
    public final List<Cart> carts;
    public final String cartStatus;

    public CheckOutCartEvent(String cartId, List<Cart>carts, String cartStatus){
        this.cartId=cartId;
        this.carts=carts;
        this.cartStatus=cartStatus;
    }
}
