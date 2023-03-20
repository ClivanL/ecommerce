package com.example.main.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    final private SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository){
        this.sessionRepository=sessionRepository;
    }

    public void saveSession(Session session){
        sessionRepository.save(session);
    }
}
