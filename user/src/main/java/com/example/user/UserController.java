package com.example.user;


import com.example.user.data.Sess;
import com.example.user.mapstruct.mappers.MapStructMapper;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository, MapStructMapper mapStructMapper, AuthenticationManager authenticationManager){
        this.userService=userService;
        this.userRepository=userRepository;
        this.mapStructMapper=mapStructMapper;
        this.authenticationManager=authenticationManager;
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

    @PostMapping(path="changePassword/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable("userId") Long userId, @RequestBody User user){

        ResponseEntity<String> response;
        try{
            response=userService.changePassword(userId, user.getPassword());
        }
        catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    //added conversion to DTO to mask password, endpoint used for JWT loadUserByUsername
    @GetMapping(path="username/{username}")
    public UserDTO getUserFromUsername(@PathVariable("username")String username){
        return mapStructMapper.userToUserDTO(userRepository.findByUsername(username).get());
    }

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
        System.out.println(attemptLogin.getPassword());
        Optional<User> user= userRepository.findByUsername(attemptLogin.getUsername());
        if (user==null){
            throw new IllegalArgumentException("wrong username or password");
        }
        else{
            User presentUser=user.get();
//            if(!(presentUser.getPassword().equals(attemptLogin.getPassword()))){
//                throw new IllegalArgumentException(presentUser.getEmail());
//            }
//            else{
//                return new Sess(presentUser.getId(),RandomStringUtils.randomAlphanumeric(15)) ;
//            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    presentUser.getUsername(), attemptLogin.getPassword());
            Authentication authentication=authenticationManager.authenticate(token);
            return new Sess(presentUser.getId(),RandomStringUtils.randomAlphanumeric(15)) ;
        }
    }



    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            System.out.println(user.toString());
            userService.createUser(user);
        }
        catch(IllegalStateException e){
            System.out.println("caughterror");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Account successfully created");
    }

    @DeleteMapping(path="{userId}")
    public void deleteUser(@PathVariable("userId") Long userId ){
        userService.deleteUser(userId);
    }

    @PutMapping(path="{userId}")
    public void editUser(@PathVariable("userId") Long userId, @RequestBody User user){
        userService.editUser(userId, user);
    }

    @PutMapping(path="updateBalance/{change}")
    public ResponseEntity<String> updateBalance(@RequestBody User user, @PathVariable("change")String change){
        try{
            userService.updateBalance(user.getId(),user.getBalance(),change);
            return ResponseEntity.status(HttpStatus.OK).body("User account balance successfully updated");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
