package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "questionattempt")
public class Questionattempt {
    @Id
    @jakarta.persistence.GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    private Long questionattemptId;
    private Long questionNo;
    private Long studentId;
    private String answer;
    @OneToOne
    @JoinColumn(name="homeworkId")
    private Homework homework;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    
}
