package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // 'class' is a reserved keyword in Java, so we use 'studentClass'
    @Column(name = "class_name", nullable = false, length = 20)
    private String studentClass;

    @Column(nullable = false, length = 10)
    private String section;

    @Column(nullable = false, length = 20)
    private String rollNo;

    @Column(unique = true, nullable = false, length = 50)
    private String scholarNo;

    @Column(length = 9)
    private String sssmid;

    @Column(length = 12)
    private String aadhaar;

    @Column(nullable = false, length = 15)
    private String gender;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 100)
    private String father_name;

    @Column(nullable = false, length = 100)
    private String mother_name;

    @Column(length = 20)
    private Status status = Status.active; // active or inactive

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
