package com.example.main.service;

import com.example.main.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("running inside here");
//        String[] usernameAndDomain = StringUtils.split(
//                username, String.valueOf(Character.LINE_SEPARATOR));//split as much as you want
        if (username == null) {
            throw new UsernameNotFoundException("Username must be provided");
        }
        System.out.println("the username is "+username);
        RestTemplate restTemplate= new RestTemplate();
        String uri="http://user-server:8081/api/user/username/"+username;
        User user = restTemplate.getForObject(uri, User.class);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username not found, username=%s",
                            username));
        }
        System.out.println("the user is "+user.toString());
        return new User(user);
    }
}
