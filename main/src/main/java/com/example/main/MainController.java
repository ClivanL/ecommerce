package com.example.main;

import com.example.main.Sess.Sess;
import com.example.main.Sess.SessController;
import com.example.main.errorHandler.RestTemplateResponseErrorHandler;
import com.example.main.models.*;
import org.apache.coyote.Response;
import org.hibernate.transform.CacheableResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping(path="/api/main")
@CrossOrigin(origins="http://localhost:5173")
public class MainController {
    private final MainService mainService;
    private final SessController sessController;

    @Autowired
    public MainController(MainService mainService, SessController sessController){

        this.mainService=mainService;
        this.sessController=sessController;

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
        List<Cart> carts= Arrays.asList(newResponse);
        final String uri3="http://item-server:8080/api/item/user/"+userId;
        Item[] listedItemsResponse=restTemplate.getForObject(uri3, Item[].class);
        List<Item>listedItems=Arrays.asList(listedItemsResponse);
        Main main= new Main(verifiedUser.getId(),verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts,listedItems);
        return main;
    }

    @PostMapping("newAccount")
    public ResponseEntity<Map<String,String>> createNewAccount(@RequestBody User user, HttpServletRequest request){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate= restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        System.out.println(user.toString());
        String uri = "http://user-server:8081/api/user";
        ResponseEntity<String> response=restTemplate.postForEntity(uri,new HttpEntity<>(user),String.class);
        System.out.println(response.getBody());
        System.out.println(response.getStatusCodeValue());
        if (response.getStatusCodeValue()!=201){
            Map<String,String> message=new HashMap<>();
            message.put("message",response.getBody());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
        Sess sessionDetails=sessController.createSessionToken(new User(user.getUsername(),user.getPassword()),request);
        Map<String,String> message=new HashMap<>();
        message.put("message","Account successfully created and session created.");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping(path="search/{searchItem}")
    public @ResponseBody List<Item> searchForItem(@PathVariable("searchItem")String searchItem){
        RestTemplate restTemplate=new RestTemplate();
        String uri="http://item-server:8080/api/item/search/"+searchItem;
        List<Item> results= Arrays.asList(restTemplate.getForObject(uri,Item[].class));
        return results;
    }
    @GetMapping(path="item/id/{id}")
    public @ResponseBody Item searchForItemById(@PathVariable("id") Long id){
        RestTemplate restTemplate=new RestTemplate();
        String uri="http://item-server:8080/api/item/"+id;
        return restTemplate.getForObject(uri,Item.class);
    }

    @GetMapping("retrieveTransactionHistory")
    public @ResponseBody List<PurchaseLog> getTransactionHistory(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if (session==null) {
            throw new IllegalStateException("You are not logged in.");
        }
        Long userId= (Long) session.getAttribute("userId");
        RestTemplate restTemplate = new RestTemplate();
        String uri="http://purchaseLog-server:8082/api/purchaselog/"+userId;
        PurchaseLog[] purchaseLogs= restTemplate.getForObject(uri,PurchaseLog[].class);
        List<PurchaseLog> purchaseHistory= new ArrayList<>();
        for (int i=0;i< purchaseLogs.length;i++){
            uri="http://item-server:8080/api/item/"+purchaseLogs[i].getItemId();
            Item retrievedItem=restTemplate.getForObject(uri,Item.class);
            uri="http://user-server:8081/api/user/"+retrievedItem.getOwnerId();
            String ownerUsername=restTemplate.getForObject(uri,User.class).getUsername();
            purchaseHistory.add(new PurchaseLog(purchaseLogs[i].getQuantity(),purchaseLogs[i].getCreatedAt(),retrievedItem, ownerUsername));
        }
        return purchaseHistory;
    }

    @GetMapping("retrieveSaleHistory")
    public @ResponseBody List<PurchaseLog> getSaleHistory(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if (session==null) {
            throw new IllegalStateException("You are not logged in.");
        }
        Long ownerId= (Long) session.getAttribute("userId");
        RestTemplate restTemplate = new RestTemplate();
        String uri="http://purchaseLog-server:8082/api/purchaselog/sale/"+ownerId;
        PurchaseLog[] saleLogs= restTemplate.getForObject(uri,PurchaseLog[].class);
        List<PurchaseLog> saleHistory= new ArrayList<>();
        for (int i=0;i< saleLogs.length;i++){
            uri="http://item-server:8080/api/item/"+saleLogs[i].getItemId();
            Item retrievedItem=restTemplate.getForObject(uri,Item.class);
            uri="http://user-server:8081/api/user/"+saleLogs[i].getUserId();
            User retrievedUser=restTemplate.getForObject(uri,User.class);
            Long retrievedUserId=retrievedUser.getId();
            String retrievedUserUsername=retrievedUser.getUsername();
            saleHistory.add(new PurchaseLog(retrievedUserId, saleLogs[i].getQuantity(),saleLogs[i].getCreatedAt(),retrievedItem, retrievedUserUsername));
        }
        return saleHistory;
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
    public Cart addToCart(@RequestBody Cart cart, HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if (session==null) {
            throw new IllegalStateException("You are not logged in.");
        }
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://cart-server:8083/api/cart";
        return restTemplate.postForObject(uri, cart, Cart.class);
    }

    @PostMapping("cart/checkOutCart")
    public Main checkOutCart(@RequestBody Main main){
        List<Cart> cartItems=main.getCartItems();
        mainService.updateMain(cartItems, main.getUserId());
    System.out.println("you're here");
        List<Cart>emptyCart= new ArrayList<>();
        Main mainCopy= new Main(main);
        mainCopy.setCartItems(emptyCart);
        return mainCopy;
    }

    @PostMapping("owner/updateQuantity")
    public ResponseEntity<Map<String,String>> updateItemQuantity(@RequestBody Item item){
        Map<String,String>message= new HashMap<>();
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri="http://item-server:8080/api/item/owner/update/"+item.getId();
        HttpEntity<Item> entity= new HttpEntity<>(item);
        ResponseEntity<String> response=restTemplate.exchange(uri,HttpMethod.PUT,entity,String.class);
        System.out.println(response.getStatusCodeValue());
        if (response.getStatusCodeValue()!=500) {
            message.put("message", "quantity successfully changed");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }
        else{
            message.put("message", response.getBody());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

    }
    @GetMapping(path="item/{category}")
    public @ResponseBody List<Item> getItems(@PathVariable("category") String category){
        RestTemplate restTemplate= new RestTemplate();
        final String uri="http://item-server:8080/api/item/category/"+category;
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
