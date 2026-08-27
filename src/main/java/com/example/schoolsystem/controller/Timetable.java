package com.example.schoolsystem.controller;


import com.example.schoolsystem.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.TestTimetable;
import com.example.schoolsystem.service.Examservice;
import com.example.schoolsystem.service.Gradeservice;
import com.example.schoolsystem.service.Testservice;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetable")
public class Timetable {
    private final Testservice testservice;
    private final Examservice examservice;
    private final Gradeservice gradeservice;

    @PostMapping("/savetesttimetable")
    public ResponseEntity<?> savetesttimetable(@RequestBody List<TestTimetable> testTimetable, HttpServletRequest request){
        Long sessionId = SessionUtil.getSessionIdAsLong(request);
        return testservice.savetesttimetable(testTimetable, sessionId);
    }

    @GetMapping("/test-timetable/{classNO}/{testName}")
    public ResponseEntity<?> gettesttimetable(String classNo,String testName,HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        Long id = Long.parseLong(sessionId);
        return testservice.gettimetable(classNo,testName,id);
    }
    @GetMapping("/testName")
    public ResponseEntity<?> getTestName(HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        Long id = Long.parseLong(sessionId);
        return testservice.getTestName(id);
    }
    @GetMapping("/testByName/{testName}")
    public ResponseEntity<?> getTestByName(@PathVariable String testName){
        return testservice.getTestByName(testName);
    }
    
    @PostMapping("/saveexamtimetable")
    public ResponseEntity<?> saveexamtimetable(@RequestBody List<ExamTimeTable> examTimeTable, HttpServletRequest request){
        Long sessionId = SessionUtil.getSessionIdAsLong(request);
        return examservice.saveexamtimetable(examTimeTable, sessionId);
    }

    @GetMapping("/exam-timetable/{classNO}/{examName}")
    public ResponseEntity<?> getexamtimetable(String classNo,String examName,HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        Long id = Long.parseLong(sessionId);
        return examservice.getexamtimetable(classNo,examName,id);
    }
    @GetMapping("/examName")
    public ResponseEntity<?> getExamName(HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        Long id = Long.parseLong(sessionId);
        return examservice.getExamName(id);
    }
    @GetMapping("/examByName/{examName}")
    public ResponseEntity<?> getExamByName(@PathVariable String examName){
        return examservice.getExamByName(examName);
    }
    @GetMapping("/grade/fill/{teacherId}/{timetableid}/{type}")
    public ResponseEntity<?> getmarkfill(@PathVariable("teacherId") Long teacherId, @PathVariable("timetableid") Long timetableid, @PathVariable("type") String type,HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        Long id = Long.parseLong(sessionId);
        return gradeservice.getgradefillbyteacherId(teacherId, timetableid,id,type);
    }
    @DeleteMapping("/testtime/{testId}")
    public ResponseEntity<?> deletetesttime(@PathVariable Long testId) {
        return testservice.deletetimetable(testId);
    }
    @DeleteMapping("/examtime/{examid}")
    public ResponseEntity<?> deleteexamtimetable(@PathVariable Long examid) {
        return examservice.deleteexmatimetable(examid);
    }
}
