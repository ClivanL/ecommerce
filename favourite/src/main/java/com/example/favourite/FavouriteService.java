package com.example.favourite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<String> setFavourite(Favourite favourite) throws Exception {
        RestTemplate restTemplate=new RestTemplate();
        Optional<List<Favourite>>checkFavourite=favouriteRepository.findByUserIdAndItemId(favourite.getUserId(),favourite.getItemId());
        String uri="http://item-server:8080/api/item/";
        String outcome="";
        if (checkFavourite.get().size()>=1){
            List<Favourite> favourites=checkFavourite.get();
            for (int i=0;i<favourites.size();i++) {
                favouriteRepository.delete(favourites.get(i));
            }
            outcome = "Item unliked";
            uri = uri + "unlike/" + favourite.getItemId();
        }
        else{
            favouriteRepository.save(favourite);
            outcome="Item liked";
            uri=uri+"like/"+favourite.getItemId();
        }
        ResponseEntity<String>response=restTemplate.getForEntity(uri,String.class);
        if (response.getStatusCode()!= HttpStatus.OK){
            throw new Exception("Error encountered when trying to update item");
        }
        return ResponseEntity.ok(outcome);
    }
    public List<Favourite>getFavouritesByUserId(Long userId){
        return favouriteRepository.findByUserId(userId);
    }
}
