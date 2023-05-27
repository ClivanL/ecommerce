package com.example.coreapi.events;

import com.example.coreapi.models.ACart;
import com.example.coreapi.models.AItem;

import java.util.List;

public class QuantityDeductedEvent {
    public final String deductionId;
    public final String cartId;

    public final List<ACart> carts;

    public final List<AItem> items;

    public QuantityDeductedEvent(String deductionId, String cartId, List<ACart> carts, List<AItem> items){
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
        this.items=items;
    }
}
