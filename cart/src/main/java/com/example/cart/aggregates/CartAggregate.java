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
    public List<Cart> carts;
    public CartStatus cartStatus;
    private final CartRepository cartRepository;

    public CartAggregate(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @CommandHandler
    public CartAggregate(CheckoutCartCommand checkoutCartCommand, CartRepository cartRepository){
        this.cartRepository = cartRepository;
        AggregateLifecycle.apply(new CheckOutCartEvent(checkoutCartCommand.cartId,checkoutCartCommand.carts, checkoutCartCommand.cartStatus));
    }
    @EventSourcingHandler
    protected void on(CheckOutCartEvent checkOutCartEvent){
        this.cartId= checkOutCartEvent.cartId;
        this.carts= checkOutCartEvent.carts;
        this.cartStatus= checkOutCartEvent.cartStatus;
    }
    @CommandHandler
    protected void on(UpdateCartStatusCommand updateCartStatusCommand){
        AggregateLifecycle.apply(new CartCheckedOutEvent(updateCartStatusCommand.cartId, updateCartStatusCommand.cartStatus));
    }
    @EventSourcingHandler
    protected void on(CartCheckedOutEvent cartCheckedOutEvent){
        Long userId=carts.get(0).getUserId();
        cartRepository.deleteAllByUserId(userId);
        this.cartId=cartCheckedOutEvent.cartId;
        this.cartStatus=CartStatus.valueOf(cartCheckedOutEvent.cartStatus);
    }

}
