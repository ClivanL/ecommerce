package com.example.main.Sess;


import com.example.main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path="/home")
@CrossOrigin(origins="http://localhost:5173")
public class SessController {

    private final SessService sessService;
    private final SessRepository sessRepository;


    @Autowired
    public SessController(SessService sessService, SessRepository sessRepository){
        this.sessService=sessService;
        this.sessRepository=sessRepository;
    }

    @PostMapping("login")
    public @ResponseBody Sess createSessionToken(@RequestBody User attemptLogin, HttpServletRequest request){
        System.out.println("inside login attempt");
        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://user-server:8081/api/user/loginForToken";
        String sessionToken= restTemplate.postForObject(uri,attemptLogin, String.class);
        final String uri2="http://user-server:8081/api/user/login";
        User verifiedUser = restTemplate.postForObject(uri2,attemptLogin, User.class);
        Long userId=verifiedUser.getId();
        Sess userSession= new Sess(userId, sessionToken);
        sessService.saveSession(userSession);
        HttpSession session=request.getSession();
        session.setAttribute("sessionDetails",userId);
        System.out.println("your session details here"+session.getAttribute("sessionDetails").toString());
        return userSession;
    }
}
