package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="salary")
public class Salaryslip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long salayId;
    private Double salary;
    private Double advancedpay;
    @OneToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;
    @Enumerated(EnumType.STRING)
    private PayrollStatus status;

    private int machineId;


}
