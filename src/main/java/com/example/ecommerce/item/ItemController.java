package com.example.ecommerce.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService=itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
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
}

