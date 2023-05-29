package com.example.coreapi.events;

public class QuantityReversedEvent {
    public final String additionId;
    public final String cartId;

    public QuantityReversedEvent(String additionId, String cartId){
        this.additionId=additionId;
        this.cartId=cartId;
    }
}
