package com.example.ecommerce.mapstruct.mappers;

import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-21T22:40:08+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( user.getId() != null ) {
            userDTO.setId( user.getId().intValue() );
        }
        userDTO.setUsername( user.getUsername() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setName( user.getName() );

        return userDTO;
    }

    @Override
    public User UserDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( (long) userDTO.getId() );
        user.setUsername( userDTO.getUsername() );
        user.setName( userDTO.getName() );
        user.setEmail( userDTO.getEmail() );

        return user;
    }
}
