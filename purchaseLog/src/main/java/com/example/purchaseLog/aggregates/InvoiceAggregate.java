package com.example.purchaseLog.aggregates;

import com.example.coreapi.models.AItem;
import com.example.purchaseLog.PurchaseLog;
import com.example.purchaseLog.PurchaseLogService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.example.coreapi.commands.CreateInvoiceCommand;
import com.example.coreapi.events.InvoiceCreatedEvent;

import java.util.HashMap;
import java.util.Map;

@Aggregate
public class InvoiceAggregate {
    @AggregateIdentifier
    private String invoiceId;
    private String paymentId;
    private String deductionId;
    private String cartId;
    private InvoiceStatus invoiceStatus;
    private PurchaseLogService purchaseLogService;
    public InvoiceAggregate(){

    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand, PurchaseLogService purchaseLogService){
        //commands here to insert purchase logs
        this.purchaseLogService=purchaseLogService;
        try{
            Map<Long, Long> ownerMap=new HashMap<>();
            for (int i=0;i<createInvoiceCommand.items.size();i++){
                ownerMap.put(createInvoiceCommand.items.get(i).getId(),createInvoiceCommand.items.get(i).getOwnerId());
            }
            Long userId=createInvoiceCommand.carts.get(0).getUserId();
            for (int i=0;i<createInvoiceCommand.carts.size();i++){
                this.purchaseLogService.addPurchaseLog(new PurchaseLog(createInvoiceCommand.carts.get(i).getItemId(),userId,createInvoiceCommand.carts.get(i).getQuantity(),ownerMap.get(createInvoiceCommand.carts.get(i).getItemId())));
            }
            AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.invoiceId,createInvoiceCommand.paymentId, createInvoiceCommand.deductionId, createInvoiceCommand.cartId, userId));
        }
        catch (Exception e){
            this.invoiceId=createInvoiceCommand.invoiceId;
            this.paymentId = createInvoiceCommand.paymentId;
            this.deductionId = createInvoiceCommand.deductionId;
            this.cartId=createInvoiceCommand.cartId;
            this.invoiceStatus=InvoiceStatus.REJECTED;
        }

    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent){
        this.invoiceId=invoiceCreatedEvent.invoiceId;
        this.paymentId = invoiceCreatedEvent.paymentId;
        this.deductionId = invoiceCreatedEvent.deductionId;
        this.cartId=invoiceCreatedEvent.cartId;
        this.invoiceStatus=InvoiceStatus.ISSUED;
    }
}
