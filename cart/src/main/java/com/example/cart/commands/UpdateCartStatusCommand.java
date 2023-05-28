package com.example.cart.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class UpdateCartStatusCommand {
    @TargetAggregateIdentifier
    public final String cartId;
    public final String cartStatus;
    public final Long userId;

    public UpdateCartStatusCommand(String cartId, String cartStatus, Long userId){
        this.cartId=cartId;
        this.cartStatus=cartStatus;
        this.userId=userId;
    }
}
