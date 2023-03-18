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
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCart(Cart cart){
        Cart cartUpdate=cartRepository.findByUserIdAndItemId(cart.getUserId(),cart.getItemId());
        cartUpdate.setQuantity(cart.getQuantity());
    }
}
