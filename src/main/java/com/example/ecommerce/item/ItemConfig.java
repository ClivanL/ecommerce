package com.example.ecommerce.item;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ItemConfig {

    @Bean
    CommandLineRunner commandLineRunner(ItemRepository repository){
        return args -> {
            Item chocolates = new Item("Chocolates", 3.50, "cadburry",
                    "www.image.com", 22
            );
            Item dimsum = new Item("Dim Sum", 0.50, "Dim Sum",
                    "www.image.com", 22
            );
            repository.saveAll(
                    List.of(chocolates, dimsum)
            );
        };
    }
}
