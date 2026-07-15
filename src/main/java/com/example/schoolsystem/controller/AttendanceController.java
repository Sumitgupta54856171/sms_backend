package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.AttendanceRequestDTO;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.service.AttendanceService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> saveAttendance(@RequestBody List<AttendanceRequestDTO> data,@CookieValue(value = "sessionId",required = false) String sessionId){
        Long id = Long.parseLong(sessionId);
        return attendanceService.saveAttendance(data,id);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getAllAttendance(@PathVariable("date") LocalDate date, @CookieValue(value = "sessionId",required = false) String sessionId,@RequestHeader(value = "Authorization") String token)
    {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        String tokenvalue = token.substring(7);
        System.out.println("check the value of token "+tokenvalue);
        Long id = Long.parseLong(sessionId);
        return attendanceService.getAllAttendance(date,id,tokenvalue);
    }

    @PutMapping("/{studentId}/{status}/{date}")
    public ResponseEntity<?> editingAttendance(@PathVariable("studentId") Long studentId,@PathVariable("status") String status,@PathVariable("date") LocalDate date){
        return attendanceService.editingAttendance(studentId,date,status);
    }


}
