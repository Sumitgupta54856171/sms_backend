package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.service.Feeservice;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fee")
public class Feecontroller {
    public final Feeservice feeservice;


    @GetMapping("/student/fees/{studentId}")
    public ResponseEntity<?> getAnnulFees(@PathVariable("studentId") Long studentId,@CookieValue("sessionId") String id){
        Long sessionId = Long.parseLong(id);
        return ResponseEntity.ok(feeservice.getStudentTotalFee(studentId,sessionId));
    }

    @GetMapping("/student/{id}/session/{sessionName}")
    public ResponseEntity<?> getrecords(){

        return ResponseEntity.ok("fetch details");
    }
    @PostMapping("/student/fees/collection/invoice")
    public ResponseEntity<?> saveInvoice(@RequestBody PaymentRequestDto paymentRequestDto){
        return ResponseEntity.ok(feeservice.generatedInvoice(paymentRequestDto));
    }
    @GetMapping("/student/{studentId}/fee")
    public ResponseEntity<?> getfeesprofile(@CookieValue("sessionId") String id,@PathVariable("studentId") Long  studentId){
        Long sessionId = Long.parseLong(id);
        return ResponseEntity.ok(feeservice.getfeeprofiledetail(studentId,sessionId));
    }
    @GetMapping("/get/invoice/{EnrollmentId}")
    public ResponseEntity<?> getInvoicebyEnrollmentId(@PathVariable("EnrollmentId") Long EnrollmentId){
        return ResponseEntity.ok(feeservice.getInvoicebyEnrollmentId(EnrollmentId));
    }
    @GetMapping("/get/session/sessionName/{studentId}")
    public ResponseEntity<?> getsessionNamewithEnrollmentId(@CookieValue("sessionId")String sessionId,@PathVariable("studentId")Long studentId){
        Long sessionid = Long.parseLong(sessionId);
        return ResponseEntity.ok(feeservice.getEnrollmentnoAndSessionName(studentId));
    }
    @GetMapping("/session-wise/history/{studentId}")
    public ResponseEntity<?> getSessionWiseHistory(@PathVariable("studentId")Long studentId){

        return ResponseEntity.ok(feeservice.getSessionbasedfees(studentId));
    }

    @GetMapping("/invoice/history/{startdate}/{endDate}")
    public ResponseEntity<?> getInvoicehistory(@CookieValue("sessionId")String session,@PathVariable("startdate")String startdate,@PathVariable("endDate")String endDate){
        Long sessionId = Long.parseLong(session);
        return ResponseEntity.ok(feeservice.getSessionInvoice(sessionId,startdate,endDate));
    }

    @GetMapping("/invoice/summary")
    public ResponseEntity<?> getInvoicesummary(@CookieValue("sessionId") String session)
    {
        Long sessionId = Long.parseLong(session);
        return ResponseEntity.ok(feeservice.getfeecollections(sessionId));
    }
    @PutMapping("/update/fees/{studentId}/{discountamount}")
    public ResponseEntity<?> updatefeesbydisamount(@PathVariable("studentId") Long studentId,@PathVariable("discountamount") Long disamount,@CookieValue("sessionId") String id)
    {
        Long sessionid = Long.parseLong(id);
        return ResponseEntity.ok(feeservice.setdiscount(studentId,sessionid,disamount));
    }
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<?> getinvoice(@PathVariable("invoiceId")Long invoiceid){
        return ResponseEntity.ok(feeservice.getInvoice(invoiceid));
    }
  
}
