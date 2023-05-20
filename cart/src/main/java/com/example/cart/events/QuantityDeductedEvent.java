package com.example.cart.events;

public class QuantityDeductedEvent {
    public final String deductionId;
    public final String cartId;

    public QuantityDeductedEvent(String deductionId, String cartId){
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
