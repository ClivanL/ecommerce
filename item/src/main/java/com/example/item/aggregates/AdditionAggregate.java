package com.example.item.aggregates;

import com.example.coreapi.commands.ReverseQuantityCommand;
import com.example.coreapi.events.DeductionFailedEvent;
import com.example.coreapi.events.QuantityDeductedEvent;
import com.example.coreapi.events.QuantityReversedEvent;
import com.example.coreapi.models.ACart;
import com.example.item.ItemService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
public class AdditionAggregate {
    @AggregateIdentifier
    private String additionId;
    private String cartId;
    private ItemService itemService;

    public AdditionAggregate(){

    }
    @CommandHandler
    public AdditionAggregate(ReverseQuantityCommand reverseQuantityCommand, ItemService itemService){
        try{
            List<ACart> carts=reverseQuantityCommand.carts;
            for (int i=0;i<carts.size();i++){
                itemService.addQuantityAxon(carts.get(i).getItemId(),carts.get(i).getQuantity());
            }
            AggregateLifecycle.apply(new QuantityReversedEvent(reverseQuantityCommand.additionId, reverseQuantityCommand.cartId));
        }
        catch(Exception e){
            System.out.println("Exception caught");
        }

    }
    @EventSourcingHandler
    protected void on(QuantityReversedEvent quantityReversedEvent){
        this.additionId=quantityReversedEvent.additionId;
        this.cartId=quantityReversedEvent.cartId;
    }
}
