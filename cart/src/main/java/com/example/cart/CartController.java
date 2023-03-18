package com.example.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Autowired
    public CartController (CartRepository cartRepository, CartService cartService){
        this.cartRepository=cartRepository;
        this.cartService=cartService;
    }

    @GetMapping
    public List<Cart> getCarts() { return cartService.getCarts();}

    @GetMapping(path="{userId}")
    public List<Cart> getCartItemsByUserId(@PathVariable("userId") Long userId){
        return cartService.getCartItemsByUserId(userId);
    }

}
