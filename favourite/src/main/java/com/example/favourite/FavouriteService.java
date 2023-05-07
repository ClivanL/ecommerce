package com.example.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository){
        this.favouriteRepository=favouriteRepository;
    }

    public List<Favourite> getFavourites(){
        return favouriteRepository.findAll();
    }
    public ResponseEntity<String> setFavourite(Favourite favourite){
        Optional<Favourite>checkFavourite=favouriteRepository.findByUserIdAndItemId(favourite.getUserId(),favourite.getItemId());
        String outcome="";
        if (checkFavourite.isPresent()){
            favouriteRepository.delete(checkFavourite.get());
            outcome="Item unliked";
        }
        else{
            favouriteRepository.save(favourite);
            outcome="Item liked";
        }
        return ResponseEntity.ok(outcome);
    }
}
