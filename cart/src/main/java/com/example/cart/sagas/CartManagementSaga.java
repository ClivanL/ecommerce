package com.example.cart.sagas;

import com.example.cart.aggregates.CartStatus;
import com.example.cart.commands.DeductQuantityCommand;
import com.example.cart.commands.PaymentCommand;
import com.example.cart.commands.UpdateCartStatusCommand;
import com.example.cart.events.CartCheckedOutEvent;
import com.example.cart.events.CheckOutCartEvent;
import com.example.cart.events.PaymentCompletedEvent;
import com.example.cart.events.QuantityDeductedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import javax.inject.Inject;
import java.time.Duration;
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

        commandGateway.send(new DeductQuantityCommand(deductionId, checkOutCartEvent.cartId,checkOutCartEvent.carts));
    }

    @SagaEventHandler(associationProperty = "deductionId")
    public void handle(QuantityDeductedEvent quantityDeductedEvent){
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
