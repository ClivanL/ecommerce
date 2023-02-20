package com.example.ecommerce.purchaseLog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public class PurchaseLogConfig {
    @Bean
    CommandLineRunner commandLineRunner3(PurchaseLogRepository purchaseLogRepository, PurchaseLogMapper purchaseLogMapper){
        return args -> {
          PurchaseLogDTO purchaselogdto= new PurchaseLogDTO(1L,1L);
          PurchaseLog purchaselog= purchaseLogMapper.toEntity(purchaselogdto);
          purchaseLogRepository.saveAll(List.of(purchaselog));
        };
    }
}
