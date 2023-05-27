package com.example.item.aggregates;

import com.example.item.Item;
import com.example.item.ItemService;
import com.example.coreapi.commands.DeductQuantityCommand;
import com.example.coreapi.events.QuantityDeductedEvent;
import com.example.coreapi.models.AItem;
import com.example.item.models.Cart;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class DeductionAggregate {
    @AggregateIdentifier
    private String deductionId;

    private String cartId;

    private DeductionStatus deductionStatus;

    private final ItemService itemService;


    public DeductionAggregate(ItemService itemService){
        this.itemService = itemService;
    }

    @CommandHandler
    public DeductionAggregate(DeductQuantityCommand deductQuantityCommand, ItemService itemService){
        this.itemService = itemService;
        System.out.println("deduct command passed");
        System.out.println(deductQuantityCommand.carts.get(0).toString());
        try{
            List<AItem>items=new ArrayList<>();
            List<Cart>carts=new ArrayList<>();
            for (int i=0;i<deductQuantityCommand.carts.size();i++){
                carts.add(new Cart(deductQuantityCommand.carts.get(i).getUserId(),deductQuantityCommand.carts.get(i).getItemId(),deductQuantityCommand.carts.get(i).getQuantity()));
            }
            System.out.println(carts.get(0).toString());
            for (int i=0; i<carts.size();i++){
                this.itemService.updateItemQuantityAxon(carts.get(i).getItemId(),carts.get(i).getQuantity());
                Item item=this.itemService.findItemById(carts.get(i).getItemId());
                items.add(new AItem(item.getId(), item.getItemName(), item.getPrice(), item.getDescription(), item.getImageUrl(), item.getQuantity(), item.getCategory(), item.getOwnerId()));
            }
            System.out.println("item deduction done");
            AggregateLifecycle.apply(new QuantityDeductedEvent(deductQuantityCommand.deductionId, deductQuantityCommand.cartId,deductQuantityCommand.carts,items));
        }
        catch(Exception e){
            System.out.println("Exception caught");
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
