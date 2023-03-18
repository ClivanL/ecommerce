package com.example.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("userId")
//    public List<Cart> getCartItemsByUserId(@RequestParam("userId") Long userId){
//        return cartService.getCartItemsByUserId(userId);
//    }

    @PostMapping
    public void addCartItem(@RequestBody Cart cart) {
        cartService.addCartItem(cart);
    }

    @PutMapping
    public void editCart(@RequestBody Cart cart){
        cartService.updateCart(cart);
    }

}
