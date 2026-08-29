package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.TestTimetable;
import com.example.schoolsystem.service.Examservice;
import com.example.schoolsystem.service.Gradeservice;
import com.example.schoolsystem.service.Testservice;
import com.example.schoolsystem.util.SessionUtil;
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
    public ResponseEntity<?> savetesttimetable(@RequestBody List<TestTimetable> testTimetable, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return testservice.savetesttimetable(testTimetable, id);
    }

    @GetMapping("/test-timetable/{classNO}/{testName}")
    public ResponseEntity<?> gettesttimetable(String classNo, String testName, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return testservice.gettimetable(classNo, testName, id);
    }
    @GetMapping("/testName")
    public ResponseEntity<?> getTestName(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return testservice.getTestName(id);
    }
    @GetMapping("/testByName/{testName}")
    public ResponseEntity<?> getTestByName(@PathVariable String testName){
        return testservice.getTestByName(testName);
    }
    
    @PostMapping("/saveexamtimetable")
    public ResponseEntity<?> saveexamtimetable(@RequestBody List<ExamTimeTable> examTimeTable, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return examservice.saveexamtimetable(examTimeTable, id);
    }

    @GetMapping("/exam-timetable/{classNO}/{examName}")
    public ResponseEntity<?> getexamtimetable(String classNo, String examName, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return examservice.getexamtimetable(classNo, examName, id);
    }
    @GetMapping("/examName")
    public ResponseEntity<?> getExamName(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return examservice.getExamName(id);
    }
    @GetMapping("/examByName/{examName}")
    public ResponseEntity<?> getExamByName(@PathVariable String examName){
        return examservice.getExamByName(examName);
    }
    @GetMapping("/grade/fill/{teacherId}/{timetableid}/{type}")
    public ResponseEntity<?> getmarkfill(@PathVariable("teacherId") Long teacherId, @PathVariable("timetableid") Long timetableid, @PathVariable("type") String type, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return gradeservice.getgradefillbyteacherId(teacherId, timetableid, id, type);
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
