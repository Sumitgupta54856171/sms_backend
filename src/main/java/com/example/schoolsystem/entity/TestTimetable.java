package com.example.schoolsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_timetable")
public class TestTimetable {
    @Id
    private Long testtimetableId;
    private String timetableName;
    private String ClassNO;     
    private String subject;
    private String teacher;
    private String day;
    private Long testcode;
    private Long sessionId;
    private Long maxMarks;
    
}
