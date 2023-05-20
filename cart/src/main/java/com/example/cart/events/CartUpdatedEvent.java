package com.example.cart.events;

public class CartUpdatedEvent {
    public final String cartId;
    public final String cartStatus;

    public CartUpdatedEvent(String cartId, String cartStatus){
        this.cartId=cartId;
        this.cartStatus=cartStatus;
    }
}
