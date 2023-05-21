package com.example.cart.commands;

import com.example.cart.Cart;
import com.example.cart.aggregates.CartStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class CheckoutCartCommand {
    @TargetAggregateIdentifier
    public final String cartId;
    public final List<Cart> carts;
    public final CartStatus cartStatus;

    public CheckoutCartCommand(String cartId, List<Cart>carts, CartStatus cartStatus){
        this.cartId=cartId;
        this.carts=carts;
        this.cartStatus=cartStatus;
    }

}
