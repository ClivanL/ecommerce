package com.example.cart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class PaymentCommand {
    @TargetAggregateIdentifier
    public final String paymentId;
    public final String deductionId;
    public final String cartId;

    public PaymentCommand(String paymentId, String cartId, String deductionId){
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
