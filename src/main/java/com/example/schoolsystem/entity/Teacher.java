package com.example.schoolsystem.entity;



import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(unique = true, length = 100)
    private String email;


    @Column(unique = true, nullable = false, length = 50)
    private String employee_id;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String subject_specialization;

    @Column(length = 20)
    private String gender;

    @Column(length = 12)
    private String aadhaar_id;

    @Column(length = 9)
    private String sssmid;


    private Status status; // active or inactive

    private String password;

    private Role role;

    private String education;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}