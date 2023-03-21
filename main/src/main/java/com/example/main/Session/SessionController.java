package com.example.main.Session;


import com.example.main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path="/home")
public class SessionController {

    private final SessionService sessionService;
    private final SessionRepository sessionRepository;


    @Autowired
    public SessionController(SessionService sessionService, SessionRepository sessionRepository){
        this.sessionService=sessionService;
        this.sessionRepository=sessionRepository;
    }

    @PostMapping("login")
    public void createSessionToken(@RequestBody User attemptLogin, HttpServletRequest request){
        System.out.println("inside login attempt");
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://user-server:8081/api/user/loginForToken";
        String sessionToken= restTemplate.postForObject(uri,attemptLogin, String.class);
        final String uri2="http://user-server:8081/api/user/login";
        User verifiedUser = restTemplate.postForObject(uri2,attemptLogin, User.class);
        Long userId=verifiedUser.getId();
        Session userSession= new Session(userId, sessionToken);
        sessionService.saveSession(userSession);
        HttpSession session=request.getSession();
        session.setAttribute("sessionDetails",userSession);
        System.out.println("your session details here"+session.getAttribute("sessionDetails").toString());
    }
}
