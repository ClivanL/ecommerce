package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
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



}