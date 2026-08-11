package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table
public class ElectSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long sessionId;
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @OneToOne
    @JoinColumn(name="studentId")
    private Student studentId;

    private String class_no;
}
