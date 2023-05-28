package com.example.cart.aggregates;

import com.example.cart.Cart;
import com.example.cart.CartRepository;
import com.example.cart.commands.CheckoutCartCommand;
import com.example.cart.commands.UpdateCartStatusCommand;
import com.example.cart.events.CartCheckedOutEvent;
import com.example.cart.events.CheckOutCartEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
public class CartAggregate {

    @AggregateIdentifier
    private String cartId;
    private List<Cart> carts;
    private CartStatus cartStatus;
    private CartRepository cartRepository;

    public CartAggregate(){
    }

    @CommandHandler
    public CartAggregate(CheckoutCartCommand checkoutCartCommand){
        AggregateLifecycle.apply(new CheckOutCartEvent(checkoutCartCommand.cartId,checkoutCartCommand.carts, checkoutCartCommand.cartStatus));
    }
    @EventSourcingHandler
    protected void on(CheckOutCartEvent checkOutCartEvent){
        this.cartId= checkOutCartEvent.cartId;
        this.carts= checkOutCartEvent.carts;
        this.cartStatus= checkOutCartEvent.cartStatus;
    }
    @CommandHandler
    protected void on(UpdateCartStatusCommand updateCartStatusCommand, CartRepository cartRepository){
        cartRepository.deleteAllByUserId(updateCartStatusCommand.userId);
        AggregateLifecycle.apply(new CartCheckedOutEvent(updateCartStatusCommand.cartId, updateCartStatusCommand.cartStatus));
    }
    @EventSourcingHandler
    protected void on(CartCheckedOutEvent cartCheckedOutEvent){
        this.cartId=cartCheckedOutEvent.cartId;
        this.cartStatus=CartStatus.valueOf(cartCheckedOutEvent.cartStatus);
    }

}
