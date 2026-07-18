package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.Event;
import com.example.schoolsystem.service.Calendraservice;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class Eventcontroller {

    private final Calendraservice calendraservice;

    @GetMapping("/get")
    public ResponseEntity<?> getEvent(){
        return ResponseEntity.ok(calendraservice.getEvent());
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(@RequestBody Event event){
        return ResponseEntity.ok(calendraservice.saveEvent(event));
    }
}
