package com.example.schoolsystem.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    private Long enrollmentId;
    private String paymentMethod;
    private Long studentId;
    private String scholarNo;
    private String classNo;
    private String rollNo;
    private Long sessionId;
    private Double amount;
    private String paymentType;
    private String remarks;
}