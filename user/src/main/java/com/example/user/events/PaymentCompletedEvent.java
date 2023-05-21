package com.example.user.events;

public class PaymentCompletedEvent {
    public final String paymentId;
    public final String deductionId;
    public final String cartId;

    public PaymentCompletedEvent(String paymentId, String cartId, String deductionId){
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
