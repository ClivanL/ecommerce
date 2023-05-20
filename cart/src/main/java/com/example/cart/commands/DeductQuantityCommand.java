package com.example.cart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeductQuantityCommand {
    @TargetAggregateIdentifier
    public final String deductionId;
    public final String cartId;

    public DeductQuantityCommand(String deductionId, String cartId){
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
