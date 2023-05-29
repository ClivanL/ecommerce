package com.example.coreapi.events;

import com.example.coreapi.models.ACart;
import com.example.coreapi.models.AItem;

import java.util.List;

public class PaymentFailedEvent {
    public final String paymentId;
    public final String cartId;
    public final List<ACart> carts;

    public PaymentFailedEvent(String paymentId, String cartId, List<ACart> carts){
        this.paymentId=paymentId;
        this.cartId=cartId;
        this.carts=carts;
    }
}
