package com.example.user.mapstruct.mappers;
import com.example.user.User;
import com.example.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)

public interface MapStructMapper {
    UserDTO userToUserDTO(User user);
    User UserDTOToUser(UserDTO userDTO);
}
