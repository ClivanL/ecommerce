package com.example.user.aggregates;

import com.example.user.UserService;
import com.example.user.commands.PaymentCommand;
import com.example.user.events.PaymentCompletedEvent;
import com.example.user.models.Cart;
import com.example.user.models.Item;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String deductionId;
    private String cartId;
    private PaymentStatus paymentStatus;
    private UserService userService;
    public PaymentAggregate(){
    }

    @CommandHandler
    public PaymentAggregate(PaymentCommand paymentCommand){
        try{
            List<Cart>carts=paymentCommand.carts;
            List<Item>items=paymentCommand.items;
            Map<String,Item> itemsMap= new HashMap<>();
            for (int i=0;i<items.size();i++){
                itemsMap.put(items.get(i).getId().toString(),items.get(i));
            }
            float amountToDeduct=0;
            Long userIdToDeduct=carts.get(0).getUserId();
            for (int i=0;i<carts.size();i++){
                Long itemId=carts.get(i).getItemId();
                int quantityP=carts.get(i).getQuantity();
                Double price=itemsMap.get(itemId.toString()).getPrice();
                amountToDeduct+=price*quantityP;
            }
            Map<String,Cart> cartsMap= new HashMap<>();
            for (int i=0;i<carts.size();i++){
                cartsMap.put(carts.get(i).getItemId().toString(),carts.get(i));
            }
            for (int i=0;i<items.size();i++){
                Long itemId=items.get(i).getId();
                int quantityP=cartsMap.get(itemId.toString()).getQuantity();
                Double price=items.get(i).getPrice();
                float balanceToAdd= (float) (price*quantityP);
                Long userIdToAdd=items.get(i).getOwnerId();
                this.userService.updateBalance(userIdToAdd,balanceToAdd,"add");
            }
            this.userService.updateBalance(userIdToDeduct,amountToDeduct,"deduct");
            AggregateLifecycle.apply(new PaymentCompletedEvent(paymentCommand.cartId, paymentCommand.cartId, paymentCommand.deductionId));
        }
        catch(Exception e){
            this.paymentId = paymentCommand.paymentId;
            this.deductionId = paymentCommand.deductionId;
            this.cartId=paymentCommand.cartId;
            this.paymentStatus=PaymentStatus.UNSUCCESSFUL;
        }

    }
    @EventSourcingHandler
    protected void on(PaymentCompletedEvent paymentCompletedEvent){
        this.paymentId = paymentCompletedEvent.paymentId;
        this.deductionId = paymentCompletedEvent.deductionId;
        this.cartId=paymentCompletedEvent.cartId;
        this.paymentStatus=PaymentStatus.SUCCESSFUL;
    }

}
