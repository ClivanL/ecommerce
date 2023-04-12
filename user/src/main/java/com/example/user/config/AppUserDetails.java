package com.example.user.config;

import com.example.user.User;
import com.example.user.UserRepository;
import com.example.user.UserService;
import com.example.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("Username must be provided");
        }
        User founduser;
        Optional<User> user=userRepository.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException(
                    String.format("Username not found, username=%s",
                            username));
        }
        else{
            founduser=user.get();
        }
        return new AppUserService(founduser);
    }
}
