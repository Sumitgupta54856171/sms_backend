package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance",
uniqueConstraints = @UniqueConstraint(columnNames = {"attendance_date", "enrollementId"})
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate; // e.g., "2026-06-28"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status; // PRESENT ya ABSENT


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollementId")
    private Enrollement_session enrollementId;

    private String grade;
    private Long studentId;

}
