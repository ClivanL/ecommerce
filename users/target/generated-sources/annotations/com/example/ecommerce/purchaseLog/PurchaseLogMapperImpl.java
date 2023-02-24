package com.example.ecommerce.purchaseLog;

import com.example.ecommerce.item.ItemMapper;
import com.example.ecommerce.user.UserMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-24T21:41:04+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class PurchaseLogMapperImpl implements PurchaseLogMapper {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PurchaseLogDTO toDto(PurchaseLog entity) {
        if ( entity == null ) {
            return null;
        }

        PurchaseLogDTO purchaseLogDTO = new PurchaseLogDTO();

        purchaseLogDTO.setId( entity.getId() );

        return purchaseLogDTO;
    }

    @Override
    public List<PurchaseLog> toEntity(List<PurchaseLogDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PurchaseLog> list = new ArrayList<PurchaseLog>( dtoList.size() );
        for ( PurchaseLogDTO purchaseLogDTO : dtoList ) {
            list.add( toEntity( purchaseLogDTO ) );
        }

        return list;
    }

    @Override
    public List<PurchaseLogDTO> toDto(List<PurchaseLog> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PurchaseLogDTO> list = new ArrayList<PurchaseLogDTO>( entityList.size() );
        for ( PurchaseLog purchaseLog : entityList ) {
            list.add( toDto( purchaseLog ) );
        }

        return list;
    }

    @Override
    public PurchaseLog toEntity(PurchaseLogDTO purchaseLogDTO) {
        if ( purchaseLogDTO == null ) {
            return null;
        }

        PurchaseLog purchaseLog = new PurchaseLog();

        purchaseLog.setItem( itemMapper.fromId( purchaseLogDTO.getItemId() ) );
        purchaseLog.setUser( userMapper.fromId( purchaseLogDTO.getUserId() ) );
        purchaseLog.setId( purchaseLogDTO.getId() );

        return purchaseLog;
    }
}
