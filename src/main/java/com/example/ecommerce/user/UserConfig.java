package com.example.ecommerce.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner2(UserRepository userRepository){
        return args ->{
            User user1=new User("user1","password", "appleseed", "apple.seed@seed.com");
            User user2=new User("user2","password","tomatoseed","tomato.seed@seed.com");
            userRepository.saveAll(List.of(user1,user2));
        };

    }
}
