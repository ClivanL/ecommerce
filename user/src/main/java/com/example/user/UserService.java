package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }



    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> changePassword(Long userId, String password){
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()){
            throw new IllegalStateException("User does not exist");
        }
        else{
            User foundUser=findUser.get();
            String existingPassword= foundUser.getPassword();
            if (passwordEncoder.matches(password, existingPassword)){
                throw new IllegalStateException("Password same as existing password");
            }
            foundUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(foundUser);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Password successfully changed");
    }
    public void createUser(User user) {
        Optional<User> userOptionalUsername= userRepository.findByUsername(user.getUsername());
        Optional<User> userOptionalEmail= userRepository.findByEmail(user.getEmail());
        if(userOptionalEmail.isPresent() && userOptionalUsername.isPresent()){
            throw new IllegalStateException("username and email are both in use.");
        }
        if (userOptionalUsername.isPresent()){
            throw new IllegalStateException("username in use.");
        }
        if(userOptionalEmail.isPresent()){
            throw new IllegalStateException("Email in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void editUser(Long userId, User user) {
        Optional<User> userOptional=userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new IllegalStateException("user does not exist");
        }
        User userInSystem=userRepository.findById(userId).orElseThrow();
        userInSystem.setPassword(user.getPassword());
    }

    @Transactional
    public void updateBalance(Long userId,float amount, String change){
        System.out.println("updating balance");
        Optional<User> confirmUser=userRepository.findById(userId);
        User verifiedUser;
        if (confirmUser.isPresent()){
            verifiedUser=confirmUser.get();
        }
        else{
            throw new IllegalStateException("User does not exist");
        }

        if(change.equals("deduct") && verifiedUser.getBalance()<amount){
            throw new IllegalStateException("Insufficient funds");
        }
        else {
            if (change.equals("deduct")){
                verifiedUser.setBalance(verifiedUser.getBalance()-amount);
            }
            else{
                verifiedUser.setBalance(verifiedUser.getBalance()+amount);
            }
            userRepository.save(verifiedUser);
        }
    }


}