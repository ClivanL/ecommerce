package com.example.coreapi.events;

import com.example.coreapi.models.ACart;
import com.example.coreapi.models.AItem;

import java.util.List;

public class DeductionFailedEvent {
    public final String deductionId;
    public final String cartId;


    public DeductionFailedEvent(String deductionId, String cartId){
        this.deductionId=deductionId;
        this.cartId=cartId;
    }
}
