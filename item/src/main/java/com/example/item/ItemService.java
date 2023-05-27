
package com.example.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addItem(Item item) {
        Optional<Item> itemOptional= itemRepository.findByItemName(item.getItemName());
        if (itemOptional.isPresent()){
            throw new IllegalStateException("item name exists");
        }
        itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Optional<Item> itemOptional=itemRepository.findById(itemId);
        if (itemOptional.isEmpty()){
            throw new IllegalStateException("item does not exist");
        }
        itemRepository.deleteById(itemId);
    }

    public Item findItemById(Long itemId) {return itemRepository.findById(itemId).get();}
    public List<Item> findItemsByOwnerId(Long ownerId){
        return itemRepository.findByOwnerId(ownerId);
    }

    public List<Item> findItemsByCategory(String category){
        return itemRepository.findByCategory(category);
    }

    @Transactional
    public void updateItem(Long itemId, Item item) {
        Optional<Item> itemOptional=itemRepository.findByItemName(item.getItemName());
        Item itemInSystem=itemRepository.findById(itemId).orElseThrow();
        if (!Objects.equals(item.getItemName(),itemInSystem.getItemName())) {
            if (itemOptional.isPresent()) {
                throw new IllegalStateException("item name exists");
            }
        }
        if (itemInSystem.getQuantity()<item.getQuantity()){
            throw new IllegalStateException("insufficient quantity");
        }


        itemInSystem.setItemName(item.getItemName());
        itemInSystem.setDescription(item.getDescription());
        itemInSystem.setImageUrl(item.getImageUrl());
        itemInSystem.setPrice(item.getPrice());
        itemInSystem.setQuantity(item.getQuantity());
        itemInSystem.setCategory(item.getCategory());


    }

    @Transactional
    public void updateItemQuantity(Long itemId, Item item){
        Optional <Item> itemData= itemRepository.findById(itemId);
        Item presentItem;
        if (itemData.isPresent()){
            presentItem=itemData.get();
        }
        else {
            throw new IllegalStateException("item does not exist");
        }

        if (presentItem.getQuantity()>=item.getQuantity()) {
            presentItem.setQuantity(presentItem.getQuantity() - item.getQuantity());
        }
        else{
            throw new IllegalStateException("insufficient quantity");
        }
    }

    //for axon: Deduct quantity
    @Transactional
    public void updateItemQuantityAxon(Long itemId, int quantity){
        System.out.println("axon update quantity");
        Optional <Item> itemData= itemRepository.findById(itemId);
        Item presentItem;
        if (itemData.isPresent()){
            presentItem=itemData.get();
        }
        else {
            throw new IllegalStateException("item does not exist");
        }

        if (presentItem.getQuantity()>=quantity) {
            presentItem.setQuantity(presentItem.getQuantity() - quantity);
        }
        else{
            throw new IllegalStateException("insufficient quantity");
        }
    }

    @Transactional
    public void ownerUpdateItemQuantity(Long itemId, Item item){
        Optional <Item> itemData= itemRepository.findById(itemId);
        Item presentItem;
        if (itemData.isPresent()){
            presentItem=itemData.get();
        }
        else {
            throw new IllegalStateException("item does not exist");
        }
        if (presentItem.getQuantity()>item.getQuantity()){
            throw new IllegalStateException("new quantity is less than current quantity");
        }
        presentItem.setQuantity(item.getQuantity());
    }

    @Transactional
    public void updateAverageRatings(Long itemId, float rating){
        System.out.println(itemId);
        System.out.println(rating);
        Optional <Item> itemData= itemRepository.findById(itemId);
        Item presentItem;
        if (itemData.isPresent()){
            System.out.println("found item");
            presentItem=itemData.get();
        }
        else {
            throw new IllegalStateException("item does not exist");
        }
        int currentHits=presentItem.getHits();
        int newHits=currentHits+1;
        System.out.println(presentItem.toString());
        presentItem.setHits(newHits);
        presentItem.setRating((presentItem.getRating()*currentHits+rating)/newHits);
        System.out.println(presentItem.toString());

        itemRepository.save(presentItem);
    }

    @Transactional
    public void updateItemFavouriteCount(String action, Long itemId) throws Exception {
        Optional<Item> findItem=itemRepository.findById(itemId);
        if (findItem.isEmpty()){
            throw new Exception("Item does not exist");
        }
        else {
            Item item=findItem.get();
            if (action.equals("like")) {
                item.setLikes(item.getLikes()+1);
            }
            else{
                item.setLikes(item.getLikes()-1);
            }
            itemRepository.save(item);
        }
    }
}
