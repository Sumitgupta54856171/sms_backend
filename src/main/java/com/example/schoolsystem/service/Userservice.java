package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.JwtAuthResponse;
import com.example.schoolsystem.dto.LoginRequest;
import com.example.schoolsystem.repository.Userrepo;
import com.example.schoolsystem.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class Userservice {
    private final Userrepo userrepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public String register(){
        return "registered";
    }
public ResponseEntity<JwtAuthResponse> login(LoginRequest loginRequest) throws Exception{
    Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = jwtUtils.generateJwtToken(auth);
    String role = auth.getAuthorities().iterator().next().getAuthority();
    return ResponseEntity.ok(new JwtAuthResponse(jwt,role));
    }

    public String logout(){
        return "logged out";
    }
}
