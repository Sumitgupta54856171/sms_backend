package com.example.schoolsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequestDTO{


   private LocalDate attendanceDate;
    private String status;
    private Long studentId;
    private String grade;
    private Long markedByTeacherId;
}