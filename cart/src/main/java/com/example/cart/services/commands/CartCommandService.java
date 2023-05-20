package com.example.cart.services.commands;

import com.example.cart.Cart;
import com.example.cart.CartRepository;
import com.example.cart.aggregates.CartStatus;
import com.example.cart.commands.CheckoutCartCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CartCommandService {
    private final CommandGateway commandGateway;
    private final CartRepository cartRepository;

    @Autowired
    public CartCommandService(CommandGateway commandGateway, CartRepository cartRepository) {
        this.commandGateway = commandGateway;
        this.cartRepository=cartRepository;
    }

    public CompletableFuture<String> checkoutCart(List<Cart> carts){
        return commandGateway.send(new CheckoutCartCommand(UUID.randomUUID().toString(),carts,String.valueOf(CartStatus.CHECKOUT)));
    }

}
