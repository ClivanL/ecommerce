package com.example.cart.sagas;

import com.example.cart.aggregates.CartStatus;
import com.example.coreapi.commands.DeductQuantityCommand;
import com.example.coreapi.commands.PaymentCommand;
import com.example.cart.commands.UpdateCartStatusCommand;
import com.example.cart.events.CartCheckedOutEvent;
import com.example.cart.events.CheckOutCartEvent;
import com.example.coreapi.events.PaymentCompletedEvent;
import com.example.coreapi.events.QuantityDeductedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import com.example.coreapi.models.ACart;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Saga
public class CartManagementSaga {

    @Inject
    private transient CommandGateway commandGateway;



    @StartSaga
    @SagaEventHandler(associationProperty = "cartId")
    public void handle(CheckOutCartEvent checkOutCartEvent){
        String deductionId= UUID.randomUUID().toString();
        System.out.println("Saga invoked");

        //associate Saga
        SagaLifecycle.associateWith("deductionId", deductionId);
        System.out.println("cart id" + checkOutCartEvent.cartId);

        List<ACart> carts=new ArrayList<>();
        for(int i=0;i<checkOutCartEvent.carts.size();i++){
            carts.add(new ACart(checkOutCartEvent.carts.get(i).getUserId(),checkOutCartEvent.carts.get(i).getItemId(),checkOutCartEvent.carts.get(i).getQuantity()));
        }

        commandGateway.sendAndWait(new DeductQuantityCommand(deductionId, checkOutCartEvent.cartId,carts));
    }

    @SagaEventHandler(associationProperty = "deductionId")
    public void handle(QuantityDeductedEvent quantityDeductedEvent){
        System.out.println("handling quantity deducted event");
        String paymentId = UUID.randomUUID().toString();
        System.out.println("Saga continued");

        //associate Saga with shipping
        SagaLifecycle.associateWith("paymentId", paymentId);

        //send the create payment command
        commandGateway.send(new PaymentCommand(paymentId, quantityDeductedEvent.cartId, quantityDeductedEvent.deductionId, quantityDeductedEvent.carts, quantityDeductedEvent.items));
    }

    @SagaEventHandler(associationProperty = "cartId")
    public void handle(PaymentCompletedEvent paymentCompletedEvent){
        commandGateway.send(new UpdateCartStatusCommand(paymentCompletedEvent.cartId, String.valueOf(CartStatus.PAID)));
    }

    @SagaEventHandler(associationProperty = "cartId")
    public void handle(CartCheckedOutEvent cartCheckedOutEvent){
        SagaLifecycle.end();
    }

}
