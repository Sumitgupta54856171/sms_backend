package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_teacher_assignments", uniqueConstraints = {
        // Ek class ka ek saal mein ek hi class teacher ho sakta hai
        @UniqueConstraint(columnNames = {"grade_class", "session_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassTeacherAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "grade_class", nullable = false)
    private String gradeClass; // e.g., "Grade 6"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher; // Kaunsa teacher hai

    // Hamesha Session ID link karein taaki purane saalo ka record kharab na ho
    @Column(name = "session_id", nullable = false)
    private Long sessionId;
}
