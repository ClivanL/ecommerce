package com.example.user;


import com.example.user.data.Sess;
import com.example.user.mapstruct.mappers.MapStructMapper;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.random.RandomGenerator;

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

//    @PostMapping("login")
//    public @ResponseBody UserDTO getUserDets(@RequestParam("username") String username, @RequestParam("password") String password){
//        Optional<User> user= userRepository.findByUsername(username);
//        if (user==null){
//            throw new IllegalArgumentException("wrong username or password");
//        }
//        else{
//        User presentUser=user.get();
//        if(!(presentUser.getPassword().equals(password))){
//            throw new IllegalArgumentException(presentUser.getEmail());
//        }
//        else{
//            return mapStructMapper.userToUserDTO(presentUser);
//        }
//    }
//}

//    @PostMapping("login")
//    public @ResponseBody UserDTO getUserDets(@RequestBody User attemptLogin){
//        Optional<User> user= userRepository.findByUsername(attemptLogin.getUsername());
//        if (user==null){
//            throw new IllegalArgumentException("wrong username or password");
//        }
//        else{
//            User presentUser=user.get();
//            if(!(presentUser.getPassword().equals(attemptLogin.getPassword()))){
//                throw new IllegalArgumentException(presentUser.getEmail());
//            }
//            else{
//                UserDTO check= mapStructMapper.userToUserDTO(presentUser);
//                System.out.println(check.toString());
//                return check;
//            }
//        }
//    }

    @GetMapping(path="{userId}")
    public UserDTO getUserDetails (@PathVariable("userId")Long userId){
        Optional<User> checkUser=userRepository.findById(userId);
        if (checkUser.isEmpty()){
            throw new IllegalArgumentException("user does not exist");
        }
        else {
            return mapStructMapper.userToUserDTO(checkUser.get());
        }
    }

    @PostMapping("loginForToken")
    public @ResponseBody Sess generateToken(@RequestBody User attemptLogin){
        Optional<User> user= userRepository.findByUsername(attemptLogin.getUsername());
        if (user==null){
            throw new IllegalArgumentException("wrong username or password");
        }
        else{
            User presentUser=user.get();
            if(!(presentUser.getPassword().equals(attemptLogin.getPassword()))){
                throw new IllegalArgumentException(presentUser.getEmail());
            }
            else{
                return new Sess(presentUser.getId(),RandomStringUtils.randomAlphanumeric(15)) ;
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
