package com.example.schoolsystem.controller;

import com.example.schoolsystem.entity.Questionattempt;
import com.example.schoolsystem.service.Questionattemptservice;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questionattempt")
@RequiredArgsConstructor
public class Questionattemptcontroller {

    private final Questionattemptservice questionattemptservice;


    @PostMapping("/save")
    public ResponseEntity<?> saveQuestionattempt(@RequestBody Questionattempt questionattempt){
        return ResponseEntity.ok(questionattemptservice.saveQuestionattempt(questionattempt));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getQuestionattempt(){
        return ResponseEntity.ok(questionattemptservice.getQuestionattempt());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuestionattemptById(@PathVariable Long id){
        return ResponseEntity.ok(questionattemptservice.getQuestionattemptById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestionattempt(@PathVariable Long id){
        return ResponseEntity.ok(questionattemptservice.deleteQuestionattempt(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuestionattempt(@RequestBody Questionattempt questionattempt){
        return ResponseEntity.ok(questionattemptservice.updateQuestionattempt(questionattempt));
    }
}
