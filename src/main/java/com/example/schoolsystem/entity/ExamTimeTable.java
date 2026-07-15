package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name="exam_timtable")
public class ExamTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testtimetableId;
    private String timetableName;
    private String classNO;
    private String subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String day;
    private Long examcode;
    private Long sessionId;
    private Long maxMarks;
    private String startTime;
    private String endTime;
    private LocalDate date;
}
