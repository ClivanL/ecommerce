package com.example.favourite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    Optional<Favourite> findByUserIdAndItemId(Long userId, Long itemId);
    List<Favourite>findByUserId(Long userId);
}
