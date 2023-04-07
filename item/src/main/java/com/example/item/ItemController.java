package com.example.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemService itemService, ItemRepository itemRepository){
        this.itemService=itemService;
        this.itemRepository=itemRepository;
    }

    @GetMapping
    public Item[] getItems() {
        List<Item> items=itemService.getItems();
        Item[] itemArray = new Item[items.size()];
        items.toArray(itemArray);
        return itemArray;
    }

    @GetMapping(path="search/{searchItem}")
    public @ResponseBody List<Item> getItem(@PathVariable("searchItem") String itemName){
        List<Item> items=itemRepository.search(itemName.toLowerCase());
        return items;
    }

    @GetMapping(path="user/{ownerId}")
    public @ResponseBody List<Item> getItemsByOwnerId(@PathVariable("ownerId") Long ownerId){
        return itemService.findItemsByOwnerId(ownerId);
    }
    @GetMapping(path="{itemId}")
    public @ResponseBody Item getItemById(@PathVariable("itemId")Long itemId){
        return itemRepository.findById(itemId).get();
    }


    @PostMapping("retrieveItemDetails")
    public List<Item> getItemDetails(@RequestBody Long[] itemIds){
        Item[] itemDetails = new Item[itemIds.length];
        for (int i=0; i<itemIds.length;i++){
            itemDetails[i]=itemRepository.findById(itemIds[i]).get();
        }
        return List.of(itemDetails);
    }

    @PostMapping
    public void addItem(@RequestBody Item item){
        itemService.addItem(item);
    }
    @DeleteMapping(path="{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId){
        itemService.deleteItem(itemId);
    }
    @PutMapping(path="{itemId}")
    public void updateItem(@PathVariable("itemId") Long itemId, @RequestBody Item item){
        itemService.updateItem(itemId, item);
    }
    @PutMapping("update/{itemId}")
    public void updateItemQuantity(@PathVariable("itemId") Long itemId, @RequestBody Item item) {
        itemService.updateItemQuantity(itemId, item);
    }

    @PutMapping("owner/update/{itemId}")
    public ResponseEntity<String> amendItemQuantity(@PathVariable("itemId") Long itemId, @RequestBody Item item){
        try {
            itemService.ownerUpdateItemQuantity(itemId, item);
        }
        catch (Error e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("quantity changed");
    }

}

