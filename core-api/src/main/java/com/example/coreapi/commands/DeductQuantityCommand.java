package com.example.coreapi.commands;

import com.example.coreapi.models.ACart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class DeductQuantityCommand {
    @TargetAggregateIdentifier
    public final String deductionId;
    public final String cartId;
    public final List<ACart> carts;
    public DeductQuantityCommand(String deductionId, String cartId, List<ACart> carts){
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
    }
}
