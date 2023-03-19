package com.example.user;


import com.example.user.mapstruct.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, MapStructMapper mapStructMapper){
        this.userService=userService;
        this.userRepository=userRepository;
        this.mapStructMapper=mapStructMapper;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("login")
    public @ResponseBody UserDTO getUserDets(@RequestParam("username") String username, @RequestParam("password") String password){
        Optional<User> user= userRepository.findByUsername(username);
        if (user==null){
            throw new IllegalArgumentException("wrong username or password");
        }
        else{
            User presentUser=user.get();
            if(!(presentUser.getPassword().equals(password))){
                throw new IllegalArgumentException(presentUser.getEmail());
            }
            else{
                return mapStructMapper.userToUserDTO(presentUser);
            }
        }
    }



    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long userId ){
        userService.deleteUser(userId);
    }

    @PutMapping(path="{userId}")
    public void editUser(@PathVariable("userId") Long userId, @RequestBody User user){
        userService.editUser(userId, user);
    }
}
