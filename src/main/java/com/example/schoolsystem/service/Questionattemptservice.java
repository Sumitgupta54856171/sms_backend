package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Questionattempt;
import com.example.schoolsystem.repository.Questionattemptrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Questionattemptservice {

    private final Questionattemptrepo questionattemptrepo;


    public ResponseEntity<?> saveQuestionattempt(Questionattempt questionattempt){
        questionattemptrepo.save(questionattempt);
        return ResponseEntity.ok("save questionattempt successfully");
    }
    public ResponseEntity<?> getQuestionattempt(){
        List<Questionattempt> q = questionattemptrepo.findAll();
        return ResponseEntity.ok(q);
    }
    public ResponseEntity<?> getQuestionattemptById(Long id){
        Questionattempt questionattempt = questionattemptrepo.findById(id).orElse(null);
        return ResponseEntity.ok(questionattempt);
    }
    public ResponseEntity<?> deleteQuestionattempt(Long id){
        questionattemptrepo.deleteById(id);
        return ResponseEntity.ok("delete questionattempt successfully");
    }
    public ResponseEntity<?> updateQuestionattempt(Questionattempt questionattempt){
        questionattemptrepo.save(questionattempt);
        return ResponseEntity.ok("update questionattempt successfully");
    }

}
