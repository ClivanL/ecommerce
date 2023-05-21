package com.example.cart.commands;

import com.example.cart.Cart;
import com.example.cart.models.Item;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class PaymentCommand {
    @TargetAggregateIdentifier
    public final String paymentId;
    public final String deductionId;
    public final String cartId;
    public final List<Cart> carts;
    public final List<Item> items;

    public PaymentCommand(String paymentId, String cartId, String deductionId, List<Cart> carts, List<Item> items){
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
        this.items=items;
    }
}
