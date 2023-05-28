package com.example.cart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class UpdateCartStatusCommand {
    @TargetAggregateIdentifier
    public final String cartId;
    public final String cartStatus;

    public UpdateCartStatusCommand(String cartId, String cartStatus){
        this.cartId=cartId;
        this.cartStatus=cartStatus;
    }
}
