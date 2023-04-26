package com.example.purchaseLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog,Long> {
    List<PurchaseLog> findByUserId(Long userId);
    List<PurchaseLog> findByOwnerId(Long ownerId);
    @Query(value="SELECT p FROM PurchaseLog p WHERE p.itemId=?1 and p.reviewedAt IS NOT NULL ORDER BY reviewedAt")
    Optional<List<PurchaseLog>> findByItemId(Long itemId);

    @Query(value="SELECT p FROM PurchaseLog p WHERE p.itemId=?1 and p.rating=?2 ORDER BY reviewedAt")
    Optional<List<PurchaseLog>> findByItemIdAndRating(Long itemId, Long rating);

    @Query(value="SELECT p FROM PurchaseLog p WHERE p.itemId=?1 and p.reviewedAt IS NOT NULL ORDER BY rating ASC")
    Optional<List<PurchaseLog>> findByItemIdCritical(Long itemId);

    @Query(value="SELECT p FROM PurchaseLog p WHERE p.itemId=?1 and p.reviewedAt IS NOT NULL ORDER BY rating DESC")
    Optional<List<PurchaseLog>> findByItemIdHelpful(Long itemId);
}
