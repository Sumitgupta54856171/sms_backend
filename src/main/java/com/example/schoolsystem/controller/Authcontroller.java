package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.LoginRequest;
import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.service.Userservice;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class Authcontroller {


    private final Userservice userservice;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,HttpServletResponse response ) throws Exception{

        return userservice.login(loginRequest,response);
    }
    @PostMapping("/register/role")
    public ResponseEntity<?> register(@RequestBody List<User> user){
       return ResponseEntity.ok(userservice.generatedId(user));
    }
}
