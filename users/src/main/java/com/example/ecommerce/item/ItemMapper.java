package com.example.ecommerce.item;

import com.example.ecommerce.purchaseLog.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO,Item> {

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }
}
