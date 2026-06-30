package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Sessionservice {
    private final Sessionrepo sessionrepo;

    public Session Savesession(Session session) throws Exception{
        System.out.println(session);
         return sessionrepo.save(session);

    }

}
