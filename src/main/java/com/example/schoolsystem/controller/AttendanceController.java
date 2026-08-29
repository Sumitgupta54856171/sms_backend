package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.AttendanceRequestDTO;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.service.AttendanceService;
import com.example.schoolsystem.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceController {


    private final AttendanceService attendanceService;
    

    @PostMapping("/save")
    public ResponseEntity<?> saveAttendance(@RequestBody List<AttendanceRequestDTO> data, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return attendanceService.saveAttendance(data, id);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getAllAttendance(@PathVariable("date") LocalDate date, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId, @RequestHeader(value = "Authorization") String token)
    {
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String tokenvalue = token.substring(7);
        System.out.println("check the value of token "+tokenvalue);
        Long id = Long.parseLong(sessionId);
        return attendanceService.getAllAttendance(date, id, tokenvalue);
    }

    @PutMapping("/{studentId}/{status}/{date}")
    public ResponseEntity<?> editingAttendance(@PathVariable("studentId") Long studentId,@PathVariable("status") String status,@PathVariable("date") LocalDate date){
        return attendanceService.editingAttendance(studentId,date,status);
    }
    @GetMapping("/dateAttendance/{startdate}/{enddate}")
    public ResponseEntity<?> getattendancebetweendate(@PathVariable("startdate") LocalDate stardate, @PathVariable("enddate") LocalDate enddate, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return ResponseEntity.ok(attendanceService.getAttendancebydate(stardate, enddate, id));
    }


}
