package com.example.ecommerce.item;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-23T19:29:34+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toEntity(ItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Item item = new Item();

        return item;
    }

    @Override
    public ItemDTO toDto(Item entity) {
        if ( entity == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        return itemDTO;
    }

    @Override
    public List<Item> toEntity(List<ItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Item> list = new ArrayList<Item>( dtoList.size() );
        for ( ItemDTO itemDTO : dtoList ) {
            list.add( toEntity( itemDTO ) );
        }

        return list;
    }

    @Override
    public List<ItemDTO> toDto(List<Item> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ItemDTO> list = new ArrayList<ItemDTO>( entityList.size() );
        for ( Item item : entityList ) {
            list.add( toDto( item ) );
        }

        return list;
    }
}
