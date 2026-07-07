package com.example.schoolsystem.dto;


import lombok.Data;

@Data
public class SessiondetailDto {
    private String sessionName;
    private Long sessionId;

    public SessiondetailDto(String sessionName, Long sessionId) {
        this.sessionName = sessionName;
        this.sessionId = sessionId;
    }
}
