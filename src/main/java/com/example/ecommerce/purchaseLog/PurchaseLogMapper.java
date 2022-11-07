package com.example.ecommerce.purchaseLog;

import com.example.ecommerce.item.ItemMapper;
import com.example.ecommerce.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={ItemMapper.class, UserMapper.class})
public interface PurchaseLogMapper extends EntityMapper<PurchaseLogDTO,PurchaseLog>{

//    PurchaseLogDTO toDto(PurchaseLog purchaseLog);

    @Mapping(source="itemId", target="item")
    @Mapping(source="userId", target="user")
    PurchaseLog toEntity(PurchaseLogDTO purchaseLogDTO);

    default PurchaseLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseLog purchaseLog = new PurchaseLog();
        purchaseLog.setId(id);
        return purchaseLog;
    }
}
