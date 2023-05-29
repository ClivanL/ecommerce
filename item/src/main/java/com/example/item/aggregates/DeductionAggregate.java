package com.example.item.aggregates;

import com.example.item.Item;
import com.example.item.ItemService;
import com.example.coreapi.commands.DeductQuantityCommand;
import com.example.coreapi.events.QuantityDeductedEvent;
import com.example.coreapi.events.DeductionFailedEvent;
import com.example.coreapi.models.AItem;
import com.example.item.models.Cart;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

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
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public void handle(DeductQuantityCommand deductQuantityCommand, ItemService itemService){
        this.itemService = itemService;
        System.out.println("deduct command passed");
        System.out.println(deductQuantityCommand.carts.get(0).toString());
        try{
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
        catch(CommandExecutionException e){
            AggregateLifecycle.apply(new DeductionFailedEvent(deductQuantityCommand.deductionId, deductQuantityCommand.cartId));
        }

    }

    @EventSourcingHandler
    protected void on(QuantityDeductedEvent quantityDeductedEvent){
        this.deductionId=quantityDeductedEvent.deductionId;
        this.cartId=quantityDeductedEvent.cartId;
        this.deductionStatus=DeductionStatus.Deducted;
    }

    @EventSourcingHandler
    protected void on(DeductionFailedEvent deductionFailedEvent){
        this.deductionId=deductionFailedEvent.deductionId;
        this.cartId=deductionFailedEvent.cartId;
        this.deductionStatus=DeductionStatus.Failed;
    }
}
