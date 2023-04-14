package com.example.main.Sess;


import com.example.main.controller.JwtAuthenticationController;
import com.example.main.models.JwtRequest;
import com.example.main.models.JwtResponse;
import com.example.main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtAuthenticationController jwtAuthenticationController;



    @Autowired
    public SessController(SessService sessService, SessRepository sessRepository, JwtAuthenticationController jwtAuthenticationController){
        this.sessService=sessService;
        this.sessRepository=sessRepository;
        this.jwtAuthenticationController=jwtAuthenticationController;
    }

    @PostMapping("login")
    public @ResponseBody Sess createSessionToken(@RequestBody User attemptLogin, HttpServletRequest request){
        System.out.println("inside login attempt");
        System.out.println(attemptLogin.toString());
        String jwtToken="";
        try {
            jwtToken= jwtAuthenticationController.createAuthenticationToken(new JwtRequest(attemptLogin.getUsername(), attemptLogin.getPassword())).getBody();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("after authorization");
        System.out.println(jwtToken);

        RestTemplate restTemplate = new RestTemplate();
        final String uri="http://user-server:8081/api/user/loginForToken";
        Sess sessionDetails= restTemplate.postForObject(uri,attemptLogin, Sess.class);
        Long userId=sessionDetails.getUserId();
        sessService.saveSession(sessionDetails);
        HttpSession session=request.getSession();
        session.setAttribute("userId",userId);
        if (!jwtToken.equals("")) {
            session.setAttribute("Authorization","Bearer "+jwtToken);
        }
        System.out.println(session.getAttribute("Authorization"));
        return sessionDetails;
    }

    @GetMapping("logout")
    public void invalidateSessionToken(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
