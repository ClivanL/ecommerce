package com.example.cart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CartConfig {
    @Bean
    CommandLineRunner commandLineRunner(CartRepository cartRepository){
        return args ->{
            Cart cart1 = new Cart(1L,5L,30);
            Cart cart2 = new Cart(1L,6L,34);
            Cart cart3 = new Cart(2L,7L,32);
            Cart cart4 = new Cart(1L,5L,36);
            cartRepository.saveAll(List.of(cart1,cart2,cart3,cart4));
        };
    }
}
