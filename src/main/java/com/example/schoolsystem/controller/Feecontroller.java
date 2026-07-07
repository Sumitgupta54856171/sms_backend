package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.service.Feeservice;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fee")
public class Feecontroller {
    public final Feeservice feeservice;


    @GetMapping("/student/fees/{studentId}")
    public ResponseEntity<?> getAnnulFees(@PathVariable("studentId") Long studentId){
        return ResponseEntity.ok(feeservice.getStudentTotalFee(studentId));
    }

    @GetMapping("/student/{id}/session/{sessionName}")
    public ResponseEntity<?> getrecords(){
        return ResponseEntity.ok("data is fetch");
    }
    @PostMapping("/student/fees/collection/invoice")
    public ResponseEntity<?> saveInvoice(@RequestBody PaymentRequestDto paymentRequestDto){
        return ResponseEntity.ok(feeservice.generatedInvoice(paymentRequestDto));
    }
}
