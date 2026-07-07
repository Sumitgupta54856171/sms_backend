package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="enrollment_session")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Enrollement_session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Transient
    @JsonProperty("studentId")
    private Long studentId;
    @ManyToOne
    @JoinColumn(name = "sessionIdInEnrollment")
    private Session session;

    @Transient
    @JsonProperty("sessionIdInEnrollment")
    private Long sessionIdInEnrollment;

    @Column(name="class_no")
    private String class_no;
    @Column
    private String roll_no;

    private Long total_fees;



}
