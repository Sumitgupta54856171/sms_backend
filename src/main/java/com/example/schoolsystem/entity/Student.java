package com.example.schoolsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    private String scholar_no;

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

    @Column( length = 15)
    private String phone;

    @Column( length = 100)
    private String father_name;

    @Column( length = 100)
    private String mother_name;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    private String apaarId;
    private String penId;
    private String address;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
