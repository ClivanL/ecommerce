package com.example.cart.commands;

import com.example.cart.Cart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class CheckoutCartCommand {
    @TargetAggregateIdentifier
    public final String cartId;
    public final List<Cart> carts;
    public final String cartStatus;

    public CheckoutCartCommand(String cartId, List<Cart>carts, String cartStatus){
        this.cartId=cartId;
        this.carts=carts;
        this.cartStatus=cartStatus;
    }

}
