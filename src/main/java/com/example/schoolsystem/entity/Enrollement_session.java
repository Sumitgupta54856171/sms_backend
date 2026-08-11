package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Builder;
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
    @JoinColumn(name = "session_id_in_enrollment")
    private Session session;

    @Transient
    @JsonProperty("session_id_in_enrollment")
    private Long sessionIdInEnrollment;

    @Column(name="class_no")
    private String class_no;
    @Column
    private String roll_no;

    @Column(nullable = false,columnDefinition = "biginit default 0")
    private Long total_fees=0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long dueFees = 0L;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long discountFees = 0L;

}
