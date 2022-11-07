package com.example.ecommerce.purchaseLog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class PurchaseLogConfig {
    @Bean
    CommandLineRunner commandLineRunner3(PurchaseLogRepository purchaseLogRepository, PurchaseLogMapper purchaseLogMapper){
        return args -> {
          PurchaseLogDTO purchaselogdto= new PurchaseLogDTO(1L,2L);
          PurchaseLog purchaselog1= purchaseLogMapper.toEntity(purchaselogdto);
          purchaseLogRepository.saveAll(List.of(purchaselog1));
        };
    }
}
