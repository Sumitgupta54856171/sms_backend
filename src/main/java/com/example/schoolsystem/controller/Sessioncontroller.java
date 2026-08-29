package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.service.Sessionservice;
import com.example.schoolsystem.util.SessionUtil;
import com.example.schoolsystem.payload.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class Sessioncontroller {

    private final Sessionservice sessionservice;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveSession(@RequestBody Session session) throws Exception {
        sessionservice.Savesession(session);
        return new ResponseEntity<>(new ApiResponse(true, "session saved", null), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSession(){
        return sessionservice.getSession();
    }

    @GetMapping("/switch/session/{sessionId}")
    public ResponseEntity<?> switchSession(@PathVariable("sessionId") Long sessionId, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId, HttpServletResponse response) {
        String prevSessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (prevSessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long previseSessionId = Long.parseLong(prevSessionId);
        return sessionservice.switchSession(sessionId, previseSessionId, response);
    }
}
