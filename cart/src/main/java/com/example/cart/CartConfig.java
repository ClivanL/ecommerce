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
            Cart cart1 = new Cart(1L,1L,30,false);
            Cart cart2 = new Cart(1L,2L,34,false);
            Cart cart3 = new Cart(2L,1L,32,false);
            Cart cart4 = new Cart(1L,3L,36,false);
            cartRepository.saveAll(List.of(cart1,cart2,cart3,cart4));
        };
    }
}
