package com.example.item.events;

import com.example.item.models.Cart;

import java.util.List;

public class QuantityDeductedEvent {
    public final String deductionId;
    public final String cartId;

    public QuantityDeductedEvent(String deductionId, String cartId){
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
