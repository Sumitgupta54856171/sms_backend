package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="exma_grade")
public class ExanGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long examgradeid;
    private Long studentId;
    private Long teacherId;
    private float mark;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="examid",nullable = true)
    private ExamTimeTable examTimeTable;

}
