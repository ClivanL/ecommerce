package com.example.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.userId=?1 ORDER BY id")
    List<Cart> findAllByUserId(Long userId);
    Optional<Cart> findByUserIdAndItemId(Long userId, Long itemId);
    void deleteAllByUserId(Long userId);
}
