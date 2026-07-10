package com.example.schoolsystem.dto;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InvoiceResponseDto {
    private Long invoiceId;
    private String studentName;
    private LocalDateTime invoiceDate;
    private String paymentMethod;
    private Long amount;
    public InvoiceResponseDto(Long invoiceId, String studentName, LocalDateTime invoiceDate, String paymentMethod, Long amount) {
        this.invoiceId = invoiceId;
        this.studentName = studentName;
        this.invoiceDate = invoiceDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}
