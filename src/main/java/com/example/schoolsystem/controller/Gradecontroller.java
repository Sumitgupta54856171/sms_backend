package com.example.schoolsystem.controller;

import com.example.schoolsystem.entity.ExanGrade;
import com.example.schoolsystem.entity.TestGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schoolsystem.service.Gradeservice;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grade")
@RequiredArgsConstructor
public class Gradecontroller {
    private final Gradeservice gradeservice;

    @PostMapping("/exam/mark/save")
    public ResponseEntity<?> saveGrade(@RequestBody List<ExanGrade> examGrade,@CookieValue("sessionId")String sessionId) {
        Long sessionid = Long.parseLong(sessionId);
        return gradeservice.saveexammark(examGrade,sessionid);
    }
    @PostMapping("/test/mark/save")
    public ResponseEntity<?> savetestGrade(@RequestBody List<TestGrade> testGrade){
        return gradeservice.savemark(testGrade);
    }
    @GetMapping("/get/mark/{teacherId}/{subject}/{grade}/{type}/{examid}")
    public ResponseEntity<?> getGrade(@PathVariable Long teacherId, @PathVariable String subject, @PathVariable String grade, @PathVariable String type,@CookieValue("sessionId") String sessionId,@PathVariable("examid")Long examid){
        Long id = Long.parseLong(sessionId);
        return gradeservice.getgrade(id,teacherId,subject,examid,type,grade);
    }
    @GetMapping("/get/mark/{classNo}/{testname}/{checkmark}")
    public ResponseEntity<?> getTestGrade(@PathVariable String classNo, @PathVariable String testname, @PathVariable String checkmark,@CookieValue("sessionId") String sessionId){
        Long id = Long.parseLong(sessionId);
        return gradeservice.getMarkbyclassandsession(classNo,testname,id,checkmark);
    }
    
}
