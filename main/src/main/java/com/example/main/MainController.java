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
import java.util.concurrent.TimeUnit;

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
        System.out.println(verifiedUser.toString());
        final String uri2 = "http://cart-server:8083/api/cart/"+userId;
        Cart[] response=restTemplate.getForObject(uri2, Cart[].class);
        Cart[] newResponse=new Cart[response.length];
        Boolean fulfillableCart=true;
        for (int i=0;i<response.length;i++){
            String uriForItem="http://item-server:8080/api/item/"+response[i].getItemId();
            Item itemResponse=restTemplate.getForObject(uriForItem,Item.class);
            if (response[i].getQuantity()> itemResponse.getQuantity()){
                newResponse[i]=new Cart(response[i].getId(),response[i].getQuantity(),itemResponse,false,response[i].getItemId(),response[i].getUserId());
                fulfillableCart=false;
            }
            else{
                newResponse[i]=new Cart(response[i].getId(),response[i].getQuantity(),itemResponse,response[i].getItemId(),response[i].getUserId());
            }

        }
        List<Cart> carts= Arrays.asList(newResponse);
        final String uri3="http://item-server:8080/api/item/user/"+userId;
        Item[] listedItemsResponse=restTemplate.getForObject(uri3, Item[].class);
        List<Item>listedItems=Arrays.asList(listedItemsResponse);
        final String uri4="http://favourite-server:8085/api/favourite/"+userId;
        Favourite[] userFavouritesArr=restTemplate.getForObject(uri4,Favourite[].class);
        List<Favourite> userFavourites= new ArrayList<>();
        for (int i=0;i<userFavouritesArr.length;i++){
            String uriForItem="http://item-server:8080/api/item/"+userFavouritesArr[i].getItemId();
            Item itemResponse=restTemplate.getForObject(uriForItem,Item.class);
            System.out.println(itemResponse.toString());
            userFavourites.add(new Favourite(userFavouritesArr[i].getUserId(), userFavouritesArr[i].getItemId(),itemResponse));
        }
        System.out.println(userFavourites);
        //List<Favourite>userFavourites=Arrays.asList(userFavouritesArr);
        Main main;
        if (fulfillableCart!=true){
            main= new Main(verifiedUser.getId(),verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts,listedItems, false, userFavourites, verifiedUser.getBalance());
        }
        else{
            main= new Main(verifiedUser.getId(),verifiedUser.getUsername(),verifiedUser.getName(),verifiedUser.getEmail(),carts,listedItems,true, userFavourites, verifiedUser.getBalance());
        }
        return main;
    }

    @PostMapping("changePassword")
    public ResponseEntity<Map<String,String>> changePassword(@RequestBody User userPassword, HttpServletRequest request){
        Map<String,String> sendBack= new HashMap<>();
        Long userId=(Long)request.getSession().getAttribute("userId");
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate= restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri = "http://user-server:8081/api/user/changePassword/"+userId;
        ResponseEntity<String> response= restTemplate.postForEntity(uri, new HttpEntity<User>(userPassword), String.class);
        sendBack.put("message",response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(sendBack);
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
    @GetMapping("purchaseLog/sentOut/{id}")
    public ResponseEntity<Map<String,String>> sendOutItem(@PathVariable("id") Long id){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate= restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri="http://purchaseLog-server:8082/api/purchaselog/sentOut/"+id;
        ResponseEntity<String> response= restTemplate.getForEntity(uri,String.class);
        Map<String,String> message=new HashMap<>();
        message.put("message", response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(message);
    }
    @GetMapping("purchaseLog/received/{id}")
    public ResponseEntity<Map<String,String>> receivedItem(@PathVariable("id") Long id){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate= restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri="http://purchaseLog-server:8082/api/purchaselog/received/"+id;
        ResponseEntity<String> response= restTemplate.getForEntity(uri,String.class);
        Map<String,String> message=new HashMap<>();
        message.put("message", response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(message);
    }

    @GetMapping(path="search/{searchItem}")
    public @ResponseBody List<Item> searchForItem(@PathVariable("searchItem")String searchItem){
        RestTemplate restTemplate=new RestTemplate();
        String uri="http://item-server:8080/api/item/search/"+searchItem;
        List<Item> results= Arrays.asList(restTemplate.getForObject(uri,Item[].class));
        return results;
    }
    @GetMapping(path="item/id/{id}/{filter}")
    public @ResponseBody Item searchForItemById(@PathVariable("id") Long id, @PathVariable("filter") String filter){

        //System.out.println(Long.parseLong(rating));
        RestTemplate restTemplate=new RestTemplate();
        String uri="http://purchaseLog-server:8082/api/purchaselog/review/"+id+"/"+filter;
        System.out.println(uri);
        PurchaseLog[] purchaseLogs=restTemplate.getForObject(uri, PurchaseLog[].class);
        uri="http://item-server:8080/api/item/"+id;
        Item item=restTemplate.getForObject(uri,Item.class);
        return new Item(item.getId(),item.getItemName(), item.getPrice(),item.getDescription(),item.getImageUrl(),item.getQuantity(),item.getCategory(),item.getOwnerId(), item.getRating(), item.getLikes(), purchaseLogs);
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
            purchaseHistory.add(new PurchaseLog(purchaseLogs[i].getId(),purchaseLogs[i].getQuantity(),purchaseLogs[i].getCreatedAt(),retrievedItem, ownerUsername, purchaseLogs[i].getSent(),purchaseLogs[i].getReceived(),purchaseLogs[i].getRating(),purchaseLogs[i].getComments(),purchaseLogs[i].getReviewedAt()));
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
            saleHistory.add(new PurchaseLog(saleLogs[i].getId(),retrievedUserId,saleLogs[i].getQuantity(),saleLogs[i].getCreatedAt(),retrievedItem, retrievedUserUsername, saleLogs[i].getSent(),saleLogs[i].getReceived(),saleLogs[i].getRating(),saleLogs[i].getComments(),saleLogs[i].getReviewedAt()));
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
        if (cart.getQuantity()<=0){
            throw new IllegalStateException("Quantity cannot be 0 or less");
        }
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://cart-server:8083/api/cart";
        return restTemplate.postForObject(uri, cart, Cart.class);
    }

    @PostMapping("cart/checkOutCart")
    public ResponseEntity<?> checkOutCart(@RequestBody Main main){
        try{
            System.out.println(main.toString());
            List<Cart> cartItems=main.getCartItems();
            mainService.updateMain(cartItems, main.getUserId());
            //System.out.println("you're here");
            List<Cart>emptyCart= new ArrayList<>();
            Main mainCopy= new Main(main);
            mainCopy.setCartItems(emptyCart);
            return ResponseEntity.status(HttpStatus.OK).body(mainCopy);
        }
        catch(Exception e){
            Map<String,String> responseMessage=new HashMap<>();
            responseMessage.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
    @PostMapping("cart/updateCart")
    public ResponseEntity<Map<String,String>> updateCart(@RequestBody Main main){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        List<Cart> cartItems=main.getCartItems();
        HttpStatus httpStatus;
        Map<String,String> responseMessage=new HashMap<>();
        try{
        for (int i=0;i<cartItems.size();i++){
            if (cartItems.get(i).getUserId()!=main.getUserId()){
                throw new IllegalStateException("Invalid cart!");
            }
        }
            String uri="http://cart-server:8083/api/cart/updateCart";
            ResponseEntity<String> response=restTemplate.postForEntity(uri,new HttpEntity<>(cartItems),String.class);
            if (response.getStatusCode()==HttpStatus.BAD_REQUEST){
                throw new IllegalStateException(response.getBody());
            }
            httpStatus=HttpStatus.OK;
            responseMessage.put("message",response.getBody());
        }
        catch(Exception e){
            httpStatus=HttpStatus.BAD_REQUEST;
            responseMessage.put("message", e.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(responseMessage);
    }
    @PostMapping("axon/cart/checkOutCart")
    public ResponseEntity<Map<String,String>> checkOutCartAxon(@RequestBody Main main){
        RestTemplate restTemplate= new RestTemplate();
        String uri="http://cart-server:8083/api/cart/checkoutCart";
        restTemplate.postForObject(uri,main.getCartItems(),String.class);
        try{
            TimeUnit.SECONDS.sleep(3);
        }
        catch(InterruptedException ie){
            Thread.currentThread().interrupt();
        }
        Map<String,String>responseBody=new HashMap<>();
        uri="http://cart-server:8083/api/cart/"+main.getUserId();
        Cart[] response=restTemplate.getForObject(uri,Cart[].class);
        if (response.length==0){
            responseBody.put("message","Checkout successful!");
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
        else{
            responseBody.put("message","Checkout failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
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

    @PutMapping(path="review/{id}")
    public ResponseEntity<Map<String,String>> reviewPurchase(@RequestBody PurchaseLog review, @PathVariable("id")Long id) {
        System.out.println("review comments"+review.getComments());
        System.out.println("review ratings"+review.getRating());
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate= restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri= "http://purchaseLog-server:8082/api/purchaselog/review/"+id;
        HttpEntity<PurchaseLog> entity= new HttpEntity<>(review);
        ResponseEntity<String> response=restTemplate.exchange(uri,HttpMethod.PUT,entity,String.class);
        Map<String,String>message=new HashMap<>();
        message.put("message",response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(message);

    }

    @PostMapping(path="favourite")
    public ResponseEntity<Map<String,String>> setLikeItem (@RequestBody Favourite favourite){
        RestTemplate restTemplate=new RestTemplate();
        String uri="http://favourite-server:8085/api/favourite";
        ResponseEntity<String> response= restTemplate.postForEntity(uri,favourite,String.class);
        Map<String,String> respond=new HashMap<>();
        respond.put("message",response.getBody());
        return ResponseEntity.status(response.getStatusCode()).body(respond);
    }

}
