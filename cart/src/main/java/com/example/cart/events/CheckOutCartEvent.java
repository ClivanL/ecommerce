package com.example.cart.events;

import com.example.cart.Cart;
import com.example.cart.aggregates.CartStatus;

import java.util.List;

public class CheckOutCartEvent {
    public final String cartId;
    public final List<Cart> carts;
    public final CartStatus cartStatus;

    public CheckOutCartEvent(String cartId, List<Cart>carts, CartStatus cartStatus){
        this.cartId=cartId;
        this.carts=carts;
        this.cartStatus=cartStatus;
    }
}
