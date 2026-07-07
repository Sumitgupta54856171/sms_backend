package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.SessiondetailDto;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Studentrepo;
import com.example.schoolsystem.repository.Userrepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Sessionservice {
    private final Sessionrepo sessionrepo;
    private final Studentrepo studentrepo;
    private final Enrollementrepo enrollementrepo;

    public Session Savesession(Session session) throws Exception{
        System.out.println(session);
         session.set_active(false);
         session.set_current(true);
         return sessionrepo.save(session);

    }

    public ResponseEntity<?> getSession(){
        List<Session> session = sessionrepo.findAll();
        List<SessiondetailDto> response = session.stream().map(s->new SessiondetailDto(s.getSession_name(),s.getSessionId())).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> switchSession(Long sessionId, Long previseSessionId, HttpServletResponse response) {
        System.out.println("session id is " + sessionId);
        System.out.println("previse session id is " + previseSessionId);

        Session s = sessionrepo.findById(previseSessionId).get();
        s.set_active(false);
        sessionrepo.save(s);
        Session s1 = sessionrepo.findById(sessionId).get();
        s1.set_active(true);
        sessionrepo.save(s1);

        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);

        return ResponseEntity.ok("session switched");
    }

    public  ResponseEntity<?> promptStudenttoSwitchSession(Long sessionId, Long previseSessionId){
        return ResponseEntity.ok("student need to switch session");
    }



}
