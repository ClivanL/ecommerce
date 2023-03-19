package com.example.main;

import com.example.main.models.Cart;
import com.example.main.models.User;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping(path="/api/main")
public class MainController {
    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService){
        this.mainService=mainService;
    }

    @PostMapping("login")
    public @ResponseBody Main getMainDetails(@RequestParam("username") String username, @RequestParam("password") String password){
        RestTemplate restTemplate = new RestTemplate();
        User attemptLogin = new User(username,password);
        final String uri="http://user-server:8081/api/user/login";
        User verifiedUser = restTemplate.postForObject(uri,attemptLogin, User.class);
        Long userId=verifiedUser.getId();
        final String uri2 = "http://cart-server:8083/api/cart/"+userId;
        Cart cart=restTemplate.getForObject(uri2, Cart.class, Map.of("id",1));
        Main main= new Main(verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),cart.getItemId(),cart.getQuantity(),cart.isPurchased());
        return main;
    }

}
