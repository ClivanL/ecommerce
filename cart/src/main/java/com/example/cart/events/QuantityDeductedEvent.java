package com.example.cart.events;

import com.example.cart.Cart;
import com.example.cart.models.Item;

import java.util.List;

public class QuantityDeductedEvent {
    public final String deductionId;
    public final String cartId;
    public final List<Cart> carts;
    public final List<Item> items;
    public QuantityDeductedEvent(String deductionId, String cartId, List<Cart> carts, List<Item> items){
        this.deductionId=deductionId;
        this.cartId=cartId;
        this.carts=carts;
        this.items=items;
    }
}
