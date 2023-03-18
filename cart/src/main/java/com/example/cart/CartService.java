package com.example.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
