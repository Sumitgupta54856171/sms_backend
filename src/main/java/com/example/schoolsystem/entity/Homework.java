package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="homework")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    private Long homeworkId;

    private  String title;
    private String description;
    private String type;
    private String classNo;
    private String subject;
    private String dueDate;
    private String fileName;
    private String filePath;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private int contentRows;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}


