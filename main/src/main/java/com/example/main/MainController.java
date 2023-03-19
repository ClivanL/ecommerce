package com.example.main;

import com.example.main.models.Cart;
import com.example.main.models.CartList;
import com.example.main.models.User;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
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
        Cart[] response=restTemplate.getForObject(uri2, Cart[].class);
        List<Cart> carts= Arrays.asList(response);
        Main main= new Main(verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts);
        return main;
    }

}
