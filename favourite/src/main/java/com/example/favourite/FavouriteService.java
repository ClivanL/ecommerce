package com.example.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
