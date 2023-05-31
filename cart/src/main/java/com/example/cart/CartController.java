package com.example.cart;


import com.example.cart.services.commands.CartCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/api/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private CartCommandService cartCommandService;

    @Autowired
    public CartController (CartRepository cartRepository, CartService cartService, CartCommandService cartCommandService){
        this.cartRepository=cartRepository;
        this.cartService=cartService;
        this.cartCommandService=cartCommandService;
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
    @PostMapping(path="updateCart")
    public ResponseEntity<String> updateCart(@RequestBody List<Cart> carts) {
        try {
            cartService.updateCart(carts);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart update unsuccessful!");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Cart successfully updated!");
    }

    @PutMapping
    public void editCart(@RequestBody Cart cart){
        cartService.editCart(cart);
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

    @PostMapping(path="checkoutCart")
    public CompletableFuture<String> checkoutCart(@RequestBody List<Cart> carts){
        return cartCommandService.checkoutCart(carts);
    }

}
