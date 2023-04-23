package com.example.purchaseLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog,Long> {
    List<PurchaseLog> findByUserId(Long userId);
    List<PurchaseLog> findByOwnerId(Long ownerId);
    @Query(value="SELECT p FROM PurchaseLog p WHERE p.itemId=?1 AND p.reviewedAt IS NOT NULL ORDER BY reviewedAt")
    List<PurchaseLog> findByItemId(Long itemId);
}
