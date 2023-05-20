package com.example.cart.commands;

import com.example.cart.Cart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class UpdateCartStatusCommand {
    @TargetAggregateIdentifier
    public final String cartId;
    public final String cartStatus;

    public UpdateCartStatusCommand(String cartId, String cartStatus){
        this.cartId=cartId;
        this.cartStatus=cartStatus;
    }
}
