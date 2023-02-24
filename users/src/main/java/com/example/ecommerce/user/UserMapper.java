package com.example.ecommerce.user;

import com.example.ecommerce.purchaseLog.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO,User> {

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
