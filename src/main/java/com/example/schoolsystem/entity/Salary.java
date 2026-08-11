package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "salaryshow")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salaryId;
    private Long totalSalary;
    @OneToOne
    @JoinColumn(name="teacherId")
    private Teacher teacher;
    private Long sessionId;
    private int machineId;


}
