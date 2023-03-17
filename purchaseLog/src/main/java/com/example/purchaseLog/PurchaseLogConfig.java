package com.example.purchaseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PurchaseLogConfig {
    @Bean
    CommandLineRunner commandLineRunner(PurchaseLogRepository purchaseLogRepository){
        return args -> {
            PurchaseLog purchaselog= new PurchaseLog(1L,1L);
            purchaseLogRepository.saveAll(List.of(purchaselog));
        };
    }
}
