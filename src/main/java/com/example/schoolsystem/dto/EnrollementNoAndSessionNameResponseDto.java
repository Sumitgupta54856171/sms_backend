package com.example.schoolsystem.dto;


import lombok.Data;

@Data
public class EnrollementNoAndSessionNameResponseDto {
    private Long enrollementNo;
    private String sessionName;
    public EnrollementNoAndSessionNameResponseDto(Long enrollementNo, String sessionName) {
        this.enrollementNo = enrollementNo;
        this.sessionName = sessionName;
    }
}
