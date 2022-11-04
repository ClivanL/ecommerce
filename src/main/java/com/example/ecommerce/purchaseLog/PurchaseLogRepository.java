package com.example.ecommerce.purchaseLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog,Long> {
}
