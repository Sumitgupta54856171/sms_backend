package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.JwtAuthResponse;
import com.example.schoolsystem.dto.LoginRequest;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Userrepo;
import com.example.schoolsystem.util.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public  class Userservice {
    private final Userrepo userrepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final Enrollementrepo enrollementrepo;
    private final Sessionrepo sessionrepo;

    public String register(){
        return "registered";
    }
    public ResponseEntity<JwtAuthResponse> login(LoginRequest loginRequest, HttpServletResponse response) throws Exception {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateJwtToken(auth);
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Optional<Session> e = sessionrepo.findByIs_Active(true);
        System.out.println(e.get().getSession_name()+" this is a session id");

        if (e.isPresent()) {

            System.out.println(e.get().getSessionId());
            Long sessionId = e.get().getSessionId();
            System.out.println(sessionId);

            ResponseCookie cookie = ResponseCookie.from("sessionId", sessionId.toString())
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new JwtAuthResponse(jwt, role));
        }
        return ResponseEntity.ok(new JwtAuthResponse(jwt, role));
    }

    public String logout(){
        return "logged out";
    }
}
