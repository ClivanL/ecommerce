package com.example.ecommerce.item;


import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping("search")
    public @ResponseBody Optional<Item> getItem(@RequestParam("search") String itemName){
        Optional<Item> item=itemRepository.findByItemName(itemName);
        if (item!=null){
            return item;
        }
        else{
            return null;
        }
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


