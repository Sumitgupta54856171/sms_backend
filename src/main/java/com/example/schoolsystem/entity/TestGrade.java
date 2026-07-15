package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name ="test_grade")
public class TestGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testgradeid;
    private Long studentId;
    private Long teacherId;
    private float mark;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="testid",nullable = true)
    private TestTimetable testTimetable;
}
