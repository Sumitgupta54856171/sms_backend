package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Notice;
import com.example.schoolsystem.repository.Noticerepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Noticeservice {

    private final Noticerepo noticerepo;


    public ResponseEntity<?> saveNotice(Notice notice){
        noticerepo.save(notice);
        return ResponseEntity.ok("save notice successfully");
    }
    public ResponseEntity<?> getNotice(){
        List<Notice> n = noticerepo.findAll();  
        return ResponseEntity.ok(n);
    }


}
