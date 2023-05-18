package com.example.main;

import com.example.main.errorHandler.RestTemplateResponseErrorHandler;
import com.example.main.models.Cart;
import com.example.main.models.Item;
import com.example.main.models.PurchaseLog;
import com.example.main.models.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MainService{

    @Transactional
    public void updateMain(List<Cart> cartItems, Long userId){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri="";
        try{
            //check if user has sufficient balance for cart purchase
            float totalCost;
            totalCost=checkSufficientBalance(cartItems,userId);
            System.out.println(totalCost);

            //update quantity, cart and purchaseLog in respective modules
            for (int i=0;i<cartItems.size();i++){
                updateinItemAndCartAndPurchaseLogs(cartItems.get(i).getItem().getId(),cartItems.get(i).getQuantity(),cartItems.get(i).getId(),userId, cartItems.get(i).getItem().getOwnerId());
            }

            //deduct funds from user after transaction is successful
            uri="http://user-server:8081/api/user/updateBalance/deduct";
            ResponseEntity<String> response=restTemplate.exchange(uri,HttpMethod.PUT,new HttpEntity<>(new User(userId,totalCost)),String.class);
            if (response.getStatusCode()==HttpStatus.BAD_REQUEST){
                throw new IllegalStateException(response.getBody());
            }

        }
        catch(Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
    @Transactional
    public float checkSufficientBalance(List<Cart>cartItems, Long userId) throws Exception {
        RestTemplate restTemplate= new RestTemplate();
        String uri="";
        //retrieve user balance from user module
        uri="http://user-server:8081/api/user/"+userId;
        float userBalance=restTemplate.getForObject(uri,User.class).getBalance();

        //tally total cost of cart
        float totalCost=0;
        for (int i=0;i<cartItems.size();i++){
            uri="http://item-server:8080/api/item/"+cartItems.get(i).getItem().getId();
            Double itemPrice=restTemplate.getForObject(uri,Item.class).getPrice();
            totalCost+=itemPrice*cartItems.get(i).getQuantity();
        }

        //throw exception if balance is insufficient
        if (totalCost>userBalance){
            throw new Exception("Insufficient balance");
        }
        else{
            return totalCost;
        }
    }

    @Transactional
    public void updateinItemAndCartAndPurchaseLogs(Long itemId, int quantity, Long cartId, Long userId, Long ownerId){
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        String uri="";

        //update quantity in item's database
        uri="http://item-server:8080/api/item/update/"+itemId.toString();
        HttpEntity<Item> requestUpdate= new HttpEntity<>(new Item(itemId,quantity));
        restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate,Void.class);

        //check for price of item
        uri="http://item-server:8080/api/item/"+itemId;
        Item item=restTemplate.getForObject(uri,Item.class);
        Double itemPrice=item.getPrice();

        //delete cart from database
        uri="http://cart-server:8083/api/cart/delete/"+cartId.toString();
        ResponseEntity<String>cartDeleteResponse=restTemplate.getForEntity(uri,String.class);
        if(cartDeleteResponse.getStatusCode()==HttpStatus.BAD_REQUEST){
            throw new IllegalStateException(cartDeleteResponse.getBody());
        }

        //update seller's balance
        float earnings= (float) (itemPrice*quantity);
        uri="http://user-server:8081/api/user/updateBalance/add";
        ResponseEntity<String> response=restTemplate.exchange(uri,HttpMethod.PUT,new HttpEntity<>(new User(item.getOwnerId(),earnings)),String.class);
        if (response.getStatusCode()==HttpStatus.BAD_REQUEST){
            throw new IllegalStateException(response.getBody());
        }
        //create purchaseLog in database
        uri="http://purchaseLog-server:8082/api/purchaselog";
        HttpEntity<PurchaseLog> requestUpdatePurchaseLog = new HttpEntity<>(new PurchaseLog(itemId,userId,quantity,ownerId)) ;
        restTemplate.exchange(uri,HttpMethod.POST,requestUpdatePurchaseLog,Void.class);
    }
}
