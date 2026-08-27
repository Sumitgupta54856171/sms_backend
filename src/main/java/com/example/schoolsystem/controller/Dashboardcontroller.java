package com.example.schoolsystem.controller;

import com.example.schoolsystem.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import com.example.schoolsystem.service.Dashboardservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashoard")
@RequiredArgsConstructor
public class Dashboardcontroller {

    private final Dashboardservice dashboardService;

    @GetMapping("/get/enrollment/class")
    public ResponseEntity<?> getenrollmentclass(HttpServletRequest request){
        String id = SessionUtil.getSessionId(request);
        Long sesssionId = Long.parseLong(id);
        System.out.println("check the api is work or not");
        return ResponseEntity.ok(dashboardService.getEnrollments(sesssionId));
    }

}
