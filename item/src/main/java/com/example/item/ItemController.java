package com.example.item;


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
    public Item[] getItems() {
        List<Item> items=itemService.getItems();
        Item[] itemArray = new Item[items.size()];
        items.toArray(itemArray);
        return itemArray;
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
}

