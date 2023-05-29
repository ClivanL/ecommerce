package com.example.coreapi.commands;

import com.example.coreapi.models.ACart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class ReverseQuantityCommand {
    @TargetAggregateIdentifier
    public final String additionId;
    public final String cartId;
    public final List<ACart> carts;
    public ReverseQuantityCommand(String additionId, String cartId, List<ACart> carts){
        this.additionId=additionId;
        this.cartId=cartId;
        this.carts=carts;
    }
}
