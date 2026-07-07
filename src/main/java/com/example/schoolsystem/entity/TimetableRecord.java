package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "timetable_records", uniqueConstraints = {
        // CLASH PREVENTION 1: Ek class mein, ek period mein sirf ek teacher jaa sakta hai
        @UniqueConstraint(columnNames = {"grade_class", "period_number", "session_id"}),

        // CLASH PREVENTION 2: Ek teacher, ek period mein sirf ek hi class mein ho sakta hai
        @UniqueConstraint(columnNames = {"teacher_id", "period_number", "session_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TimetableRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade_class", nullable = false)
    private String gradeClass; // e.g., "Grade 6"

    @Column(name = "subject_name", nullable = false)
    private String subjectName; // e.g., "Mathematics"

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber; // 1, 2, 3, 4, 5, 6

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Transient
    @JsonProperty("teacher_id")
    private Long teacher_id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @Transient
    @JsonProperty("session_id")
    private Long session_id;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}
