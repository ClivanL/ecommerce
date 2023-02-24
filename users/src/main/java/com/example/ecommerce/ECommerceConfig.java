package com.example.ecommerce;

import com.example.ecommerce.purchaseLog.PurchaseLog;
import com.example.ecommerce.purchaseLog.PurchaseLogDTO;
import com.example.ecommerce.purchaseLog.PurchaseLogMapper;
import com.example.ecommerce.user.User;
import org.springframework.boot.CommandLineRunner;
import com.example.ecommerce.item.Item;
import com.example.ecommerce.item.ItemRepository;
import com.example.ecommerce.purchaseLog.PurchaseLogRepository;
import com.example.ecommerce.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ECommerceConfig {

    @Bean
    CommandLineRunner commandLineRunner(ItemRepository itemRepository, UserRepository userRepository,
                                        PurchaseLogRepository purchaseLogRepository, PurchaseLogMapper purchaseLogMapper){
        return args -> {
            Item chocolates = new Item("Chocolates", 3.50, "cadburry",
                    "www.image.com", 22
            );
            Item dimsum = new Item("Dim Sum", 0.50, "Dim Sum",
                    "www.image.com", 22
            );
            itemRepository.saveAll(
                    List.of(chocolates, dimsum)
            );
            User user1=new User("user1","password", "appleseed", "apple.seed@seed.com");
            User user2=new User("user2","password","tomatoseed","tomato.seed@seed.com");
            userRepository.saveAll(List.of(user1,user2));

            PurchaseLogDTO purchaselogdto= new PurchaseLogDTO(1L,1L);
            PurchaseLog purchaselog= purchaseLogMapper.toEntity(purchaselogdto);
            purchaseLogRepository.saveAll(List.of(purchaselog));
        };
    }
}
