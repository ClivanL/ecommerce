package com.example.item.commands;

import com.example.item.models.Cart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class DeductQuantityCommand {
    @TargetAggregateIdentifier
    public final String deductionId;
    public final String cartId;
    public final List<Cart> carts;
    public DeductQuantityCommand(String deductionId, String cartId, List<Cart> carts){
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
    }
}
