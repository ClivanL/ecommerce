package com.example.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/favourite")
public class FavouriteController {
    private final FavouriteRepository favouriteRepository;
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteRepository favouriteRepository, FavouriteService favouriteService){
        this.favouriteRepository=favouriteRepository;
        this.favouriteService=favouriteService;
    }

    @GetMapping
    public List<Favourite> getFavourites(){
        return favouriteService.getFavourites();
    }
}
