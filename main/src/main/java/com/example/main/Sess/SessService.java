package com.example.main.Sess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessService {
    final private SessRepository sessRepository;

    @Autowired
    public SessService(SessRepository sessRepository){
        this.sessRepository=sessRepository;
    }

    public void saveSession(Sess session){
        sessRepository.save(session);
    }
}
