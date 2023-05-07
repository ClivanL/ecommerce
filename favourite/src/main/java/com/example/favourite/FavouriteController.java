package com.example.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<String> setFavourite(@RequestBody Favourite favourite){
        try {
            ResponseEntity<String> response = favouriteService.setFavourite(favourite);
            return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{userId}")
    public List<Favourite> getFavouritesByUserId(@PathVariable("userId")Long userId){
        return favouriteService.getFavouritesByUserId(userId);
    }
}
