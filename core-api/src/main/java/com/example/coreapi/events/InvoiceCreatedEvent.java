package com.example.coreapi.events;

public class InvoiceCreatedEvent {
    public final String invoiceId;
    public final String paymentId;
    public final String deductionId;
    public final String cartId;
    public final Long userId;

    public InvoiceCreatedEvent(String invoiceId,String paymentId,String deductionId,String cartId, Long userId){
        this.invoiceId=invoiceId;
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.userId=userId;
    }
}

