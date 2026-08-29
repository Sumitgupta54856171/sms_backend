package com.example.schoolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private String role;
    private Long sessionId;// React ko pata hona chahiye ki user Admin hai ya Teacher
}
