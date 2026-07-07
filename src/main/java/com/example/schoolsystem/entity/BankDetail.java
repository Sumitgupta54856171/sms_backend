package com.example.schoolsystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank_details")
public class BankDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankDetailId;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String AccountHolderName;
    private String branchName;
    @OneToOne
    @JoinColumn(name="student_id")
    private Student student;

}
