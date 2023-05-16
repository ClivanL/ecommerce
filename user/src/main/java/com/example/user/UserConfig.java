package com.example.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args ->{
            //password string is after "password" is bcrypt password encoded.
            User user1=new User("user1","$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", "appleseed", "apple.seed@seed.com",100);
            User user2=new User("user2","$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6","tomatoseed","tomato.seed@seed.com",100);
            userRepository.saveAll(List.of(user1,user2));
        };
    }

}
