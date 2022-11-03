package com.example.ecommerce.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
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
