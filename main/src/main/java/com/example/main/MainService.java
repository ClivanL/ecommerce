package com.example.main;

import com.example.main.models.Cart;
import com.example.main.models.Item;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MainService {

    @Transactional
    public void updateMain(List<Cart> cartItems){
        RestTemplate restTemplate= new RestTemplate();
        String uri="";
        for (int i=0;i<cartItems.size();i++){
            updateinItemAndCart(cartItems.get(i).getItem().getId(),cartItems.get(i).getQuantity(),cartItems.get(i).getId());
        }
    }

    @Transactional
    public void updateinItemAndCart(Long itemId, int quantity, Long cartId){
        RestTemplate restTemplate= new RestTemplate();
        String uri="";

        //update quantity in item's database
        uri="http://item-server:8080/api/item/update/"+itemId.toString();
        HttpEntity<Item> requestUpdate= new HttpEntity<>(new Item(itemId,quantity));
        restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate,Void.class);

        //delete cart from database
        uri="http://cart-server:8083/api/cart/"+cartId.toString();
        restTemplate.delete(uri);
    }
}
