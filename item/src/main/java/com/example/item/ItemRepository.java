package com.example.item;

import net.bytebuddy.TypeCache;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<Item> findByItemName(String itemName);
    @Query("SELECT i FROM Item i WHERE i.ownerId=?1 ORDER BY id")
    List<Item> findByOwnerId(Long ownerId);

    @Override
    @Query(value="SELECT * FROM Item WHERE quantity>0 ORDER BY id", nativeQuery = true)
    List<Item> findAll();
}
