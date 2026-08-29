package com.example.schoolsystem.controller;

import com.example.schoolsystem.service.Dashboardservice;
import com.example.schoolsystem.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashoard")
@RequiredArgsConstructor
public class Dashboardcontroller {

    private final Dashboardservice dashboardService;

    @GetMapping("/get/enrollment/class")
    public ResponseEntity<?> getenrollmentclass(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long sesssionId = Long.parseLong(sessionId);
        System.out.println(sessionId);
        System.out.println("check the api is work or not");
        return ResponseEntity.ok(dashboardService.getEnrollments(sesssionId));
    }

}
