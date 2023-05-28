package com.example.coreapi.commands;

import com.example.coreapi.models.ACart;
import com.example.coreapi.models.AItem;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class CreateInvoiceCommand {

    @TargetAggregateIdentifier
    public final String invoiceId;
    public final String paymentId;
    public final String deductionId;
    public final String cartId;
    public final List<ACart> carts;
    public final List<AItem> items;

    public CreateInvoiceCommand(String invoiceId,String paymentId,String deductionId,String cartId, List<ACart> carts,List<AItem> items){
        this.invoiceId=invoiceId;
        this.paymentId=paymentId;
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
        this.items=items;
    }
}
