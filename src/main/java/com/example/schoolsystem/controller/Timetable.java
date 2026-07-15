package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.TestTimetable;
import com.example.schoolsystem.service.Examservice;
import com.example.schoolsystem.service.Testservice;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetable")
public class Timetable {
    private final Testservice testservice;
    private final Examservice examservice;

    @PostMapping("/savetesttimetable")
    public ResponseEntity<?> savetesttimetable(@RequestBody List<TestTimetable> testTimetable, @CookieValue("sessionId") Long sessionId){
        return testservice.savetesttimetable(testTimetable, sessionId);
    }

    @GetMapping("/test-timetable/{classNO}/{testName}")
    public ResponseEntity<?> gettesttimetable(String classNo,String testName,@CookieValue("sessionId") String sessionId){
        Long id = Long.parseLong(sessionId);
        return testservice.gettimetable(classNo,testName,id);
    }
    @GetMapping("/testName")
    public ResponseEntity<?> getTestName(@CookieValue("sessionId") String sessionId){
        Long id = Long.parseLong(sessionId);
        return testservice.getTestName(id);
    }
    @GetMapping("/testByName/{testName}")
    public ResponseEntity<?> getTestByName(@PathVariable String testName){
        return testservice.getTestByName(testName);
    }
    
    @PostMapping("/saveexamtimetable")
    public ResponseEntity<?> saveexamtimetable(@RequestBody List<ExamTimeTable> examTimeTable, @CookieValue("sessionId") Long sessionId){
        return examservice.saveexamtimetable(examTimeTable, sessionId);
    }

    @GetMapping("/exam-timetable/{classNO}/{examName}")
    public ResponseEntity<?> getexamtimetable(String classNo,String examName,@CookieValue("sessionId") String sessionId){
        Long id = Long.parseLong(sessionId);
        return examservice.getexamtimetable(classNo,examName,id);
    }
    @GetMapping("/examName")
    public ResponseEntity<?> getExamName(@CookieValue("sessionId") String sessionId){
        Long id = Long.parseLong(sessionId);
        return examservice.getExamName(id);
    }
    @GetMapping("/examByName/{examName}")
    public ResponseEntity<?> getExamByName(@PathVariable String examName){
        return examservice.getExamByName(examName);
    }
}
