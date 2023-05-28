package com.example.cart.events;

public class CartCheckedOutEvent {
    public final String cartId;
    public final String cartStatus;

    public CartCheckedOutEvent(String cartId, String cartStatus){
        this.cartId=cartId;
        this.cartStatus=cartStatus;
    }
}
