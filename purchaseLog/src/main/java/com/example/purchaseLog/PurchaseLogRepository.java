package com.example.purchaseLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog,Long> {
    List<PurchaseLog> findByUserId(Long userId);
    List<PurchaseLog> findByOwnerId(Long ownerId);
}
