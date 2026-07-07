package com.example.schoolsystem.dto;




import lombok.Data;

@Data
public class BankDetailsRequestDto {
    private Long studentId;
    private String accountHolder;
    private String bankName;
    private String accountNo;
    private String ifscCode;
    private String branch;
}