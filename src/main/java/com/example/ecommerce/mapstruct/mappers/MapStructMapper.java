package com.example.ecommerce.mapstruct.mappers;

import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(
    componentModel="spring"
)


public interface MapStructMapper {

    UserDTO userToUserDTO(User user);
    User UserDTOToUser(UserDTO userDTO);
}
