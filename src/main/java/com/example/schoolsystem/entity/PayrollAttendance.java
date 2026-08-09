package com.example.schoolsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "payrollattendance")
public class PayrollAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payAttendanceID;
    private int machineId;
    private LocalDate todaydate;
    private String checkin;
    private String checkout;
    @Enumerated(EnumType.STRING)
    private Attendancestatus status;
    private String workhours;
    private String Remarks;
    private String companyName;
    @OneToOne
    @JoinColumn(name = "teacherId",nullable = true)
    private Teacher teacher;
    private LocalDate date;

}
