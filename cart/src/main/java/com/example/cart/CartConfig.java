package com.example.cart;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CartConfig {
    @Bean
    CommandLineRunner commandLineRunner(CartRepository cartRepository){
        return args ->{
            Cart cart1 = new Cart(1L,1L,30);
            Cart cart2 = new Cart(1L,2L,34);
            Cart cart3 = new Cart(2L,3L,32);
            Cart cart4 = new Cart(1L,3L,36);
            cartRepository.saveAll(List.of(cart1,cart2,cart3,cart4));
        };
    }
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);

        return xStream;
    }
}
