package com.example.main;

import com.example.main.models.Cart;
import com.example.main.models.CartList;
import com.example.main.models.Item;
import com.example.main.models.User;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api/main")
@CrossOrigin(origins="http://localhost:5173")
public class MainController {
    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService){
        this.mainService=mainService;
    }

    @PostMapping("retrieveAccountDetails")
    public @ResponseBody Main getMainDetails(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if (session==null) {
            throw new IllegalStateException("You are not logged in.");
        }
        Long userId= (Long) session.getAttribute("userId");
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://user-server:8081/api/user/"+userId;
        User verifiedUser = restTemplate.getForObject(uri,User.class);
        final String uri2 = "http://cart-server:8083/api/cart/"+userId;
        Cart[] response=restTemplate.getForObject(uri2, Cart[].class);
        List<Cart> carts= Arrays.asList(response);
        Main main= new Main(verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts);
        return main;
    }
    @PostMapping("item/new")
    public String addNewProduct(@RequestBody Item item){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Item> entity= new HttpEntity<Item>(item,headers);
        return restTemplate.exchange("http://item-server:8080/api/item", HttpMethod.POST, entity, String.class).getBody();

    }

}