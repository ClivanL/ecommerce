package com.example.coreapi.events;

import com.example.coreapi.models.ACart;
import com.example.coreapi.models.AItem;

import java.util.List;

public class PaymentCompletedEvent {
    public final String paymentId;
    public final String deductionId;
    public final String cartId;
    public final List<ACart> carts;
    public final List<AItem> items;

    public PaymentCompletedEvent(String paymentId, String cartId, String deductionId, List<ACart> carts, List<AItem> items){
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
        this.items=items;
    }
}
