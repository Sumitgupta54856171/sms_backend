package com.example.schoolsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name ="test_grade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "testtimetable_id"})

)
public class TestGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testgradeid;
    private Long studentId;
    private Long teacherId;
    private String subject;
    private Long sessionId;
    private String classNo;
    private float mark;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "testtimetable_id")
    private TestTimetable testTimetable;
}
