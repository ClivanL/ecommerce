package com.example.coreapi;

import com.example.coreapi.Cart;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class DeductQuantityCommand {
    @TargetAggregateIdentifier
    public final String deductionId;
    public final String cartId;
    public DeductQuantityCommand(String deductionId, String cartId){
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
