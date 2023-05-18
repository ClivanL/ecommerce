package com.example.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Cart addCartItem(@RequestBody Cart cart) {
        cartService.addCartItem(cart);
        return cart;
    }

    @PutMapping
    public void editCart(@RequestBody Cart cart){
        cartService.updateCart(cart);
    }

    @GetMapping(path="delete/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable("cartId") Long cartId){
       Optional<Cart> confirmCart=cartRepository.findById(cartId);
       if (confirmCart.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart does not exist");
       }
       else{
           return ResponseEntity.status(HttpStatus.OK).body("Cart successfully deleted");
       }
    }

}
