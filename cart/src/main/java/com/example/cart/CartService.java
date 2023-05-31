package com.example.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository){
        this.cartRepository=cartRepository;
    }

    public List<Cart> getCarts(){
        return cartRepository.findAll();
    }
    public List<Cart> getCartItemsByUserId(Long userId){
        List<Cart> presentCart= Collections.emptyList();
        presentCart=cartRepository.findAllByUserId(userId);
        return presentCart;
    }
    public void addCartItem(Cart cart){
        Optional<Cart> matchedCart=cartRepository.findByUserIdAndItemId(cart.getUserId(),cart.getItemId());
        if (matchedCart.isPresent()){
            System.out.println("inside hereeeeeeeee");
            Cart presentCart=matchedCart.get();
            presentCart.setQuantity(presentCart.getQuantity()+cart.getQuantity());
            cartRepository.save(presentCart);
        }
        else {
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void editCart(Cart cart){
        Optional<Cart> cartUpdate=cartRepository.findByUserIdAndItemId(cart.getUserId(),cart.getItemId());
        if (cartUpdate.isPresent()) {
            cartUpdate.get().setQuantity(cart.getQuantity());
        }
        else{
            throw new IllegalStateException("Item does not exist in cart");
        }
    }
    @Transactional
    public void updateCart(List<Cart> carts){
        Long userId=carts.get(0).getUserId();
        cartRepository.deleteAllByUserId(userId);
        cartRepository.saveAll(carts);
    }
}
