package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Event;
import com.example.schoolsystem.repository.Eventrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Calendraservice {

    private final Eventrepo eventrepo;

    public ResponseEntity<?> saveEvent(Event event){
        eventrepo.save(event);
        return ResponseEntity.ok("Event save successfully");
    }

    public ResponseEntity<?> getEvent(){
        List<Event> le = eventrepo.findAll();
        return ResponseEntity.ok(le);
    }

}
