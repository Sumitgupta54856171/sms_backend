package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

@Data
@NoArgsConstructor
@Entity
@Table(name="exma_grade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "examtimetableId"})
)
public class ExanGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long examgradeid;
    private Long studentId;
    private Long teacherId;
    private String subject;
    private Long sessionId;
    private String classNo;
    private float mark;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examtimetableId", nullable = false)
    private ExamTimeTable examtimetableId;

}
