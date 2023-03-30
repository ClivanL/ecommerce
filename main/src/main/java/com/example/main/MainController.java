package com.example.main;

import com.example.main.models.Cart;
import com.example.main.models.Item;
import com.example.main.models.User;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
        Cart[] newResponse=new Cart[response.length];
        for (int i=0;i<response.length;i++){
            String uriForItem="http://item-server:8080/api/item/"+response[i].getItemId();
            Item itemResponse=restTemplate.getForObject(uriForItem,Item.class);
            newResponse[i]=new Cart(response[i].getId(),response[i].getQuantity(),itemResponse);
        }
//        Long itemIds[]=new Long[response.length];
//        for (var i=0; i<response.length;i++) {
//            itemIds[i]=response[i].getItemId();
//        }
//        final String uri3="http://item-server:8083/api/item/retrieveItemsDetails";
//        Item[] itemDetails=restTemplate.postForObject(uri3,itemIds,Item[].class);
//        System.out.println(itemDetails[0].toString());
        List<Cart> carts= Arrays.asList(newResponse);
        Main main= new Main(verifiedUser.getId(),verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts);
        return main;
    }
    @PostMapping("item/new")
    public void addNewProduct(@RequestBody Item item){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Item> entity= new HttpEntity<Item>(item,headers);
        restTemplate.exchange("http://item-server:8080/api/item", HttpMethod.POST, entity, String.class).getBody();

    }

    @PostMapping("cart/addToCart")
    public Cart addToCart(@RequestBody Cart cart){
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://cart-server:8083/api/cart";
        return restTemplate.postForObject(uri, cart, Cart.class);
    }

    @PostMapping("cart/checkOutCart")
    public Main checkOutCart(@RequestBody Main main){
        List<Cart> cartItems=main.getCartItems();
        mainService.updateMain(cartItems);
    System.out.println("you're here");
        List<Cart>emptyCart= new ArrayList<>();
        Main mainCopy= new Main(main);
        mainCopy.setCartItems(emptyCart);
        return mainCopy;
    }

    @GetMapping("item/all")
    public @ResponseBody List<Item> getItems(){
        RestTemplate restTemplate= new RestTemplate();
        final String uri="http://item-server:8080/api/item";
        Item[] itemsArray= restTemplate.getForObject(uri,Item[].class);
        return Arrays.asList(itemsArray);
    }

    @DeleteMapping("cart/{cartId}")
    public void deleteCart(@PathVariable("cartId") Long cartId){
        RestTemplate restTemplate= new RestTemplate();
        final String uri="http://cart-server:8083/api/cart/"+cartId;
        restTemplate.delete(uri);
    }

}
