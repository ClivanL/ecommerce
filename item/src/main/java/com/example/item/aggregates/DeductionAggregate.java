package com.example.item.aggregates;

import com.example.item.Item;
import com.example.item.ItemService;
import com.example.item.commands.DeductQuantityCommand;
import com.example.item.events.QuantityDeductedEvent;
import com.example.item.models.Cart;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class DeductionAggregate {
    @AggregateIdentifier
    private String deductionId;

    private String cartId;

    private DeductionStatus deductionStatus;

    private ItemService itemService;


    public DeductionAggregate(){
    }

    @CommandHandler
    public DeductionAggregate(DeductQuantityCommand deductQuantityCommand){
        try{
            List<Item>items=new ArrayList<>();
            List<Cart>carts=deductQuantityCommand.carts;
            for (int i=0; i<carts.size();i++){
                this.itemService.updateItemQuantityAxon(carts.get(i).getItemId(),carts.get(i).getQuantity());
                items.add(itemService.findItemById(carts.get(i).getItemId()));
            }
            AggregateLifecycle.apply(new QuantityDeductedEvent(deductQuantityCommand.deductionId, deductQuantityCommand.cartId,carts,items));
        }
        catch(Exception e){
            this.deductionId=deductQuantityCommand.deductionId;
            this.cartId=deductQuantityCommand.cartId;
            this.deductionStatus=DeductionStatus.Failed;
        }

    }

    @EventSourcingHandler
    protected void on(QuantityDeductedEvent quantityDeductedEvent){
        this.deductionId=quantityDeductedEvent.deductionId;
        this.cartId=quantityDeductedEvent.cartId;
        this.deductionStatus=DeductionStatus.Deducted;
    }
}
