package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "test_timetable")
public class TestTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testtimetableId;
    private String timetableName;
    private String classNO;
    private String subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherid", nullable = true)
    private Teacher teacher;
    private String day;
    private Long testcode;
    private Long sessionId;
    private Long maxMarks;
    private LocalDate date;
    
}
