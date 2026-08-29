package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.service.Feeservice;
import com.example.schoolsystem.util.SessionUtil;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fee")
public class Feecontroller {
    public final Feeservice feeservice;


    @GetMapping("/student/fees/{studentId}")
    public ResponseEntity<?> getAnnulFees(@PathVariable("studentId") Long studentId, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return ResponseEntity.ok(feeservice.getStudentTotalFee(studentId, id));
    }


    @PostMapping("/student/fees/collection/invoice")
    public ResponseEntity<?> saveInvoice(@RequestBody PaymentRequestDto paymentRequestDto){
        return ResponseEntity.ok(feeservice.generatedInvoice(paymentRequestDto));
    }
    @GetMapping("/student/{studentId}/fee")
    public ResponseEntity<?> getfeesprofile(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId, @PathVariable("studentId") Long studentId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long id = Long.parseLong(sessionId);
        return ResponseEntity.ok(feeservice.getfeeprofiledetail(studentId, id));
    }
    @GetMapping("/get/invoice/{EnrollmentId}")
    public ResponseEntity<?> getInvoicebyEnrollmentId(@PathVariable("EnrollmentId") Long EnrollmentId){
        return ResponseEntity.ok(feeservice.getInvoicebyEnrollmentId(EnrollmentId));
    }
    @GetMapping("/get/session/sessionName/{studentId}")
    public ResponseEntity<?> getsessionNamewithEnrollmentId(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId, @PathVariable("studentId") Long studentId){
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long sessionid = Long.parseLong(sessionId);
        return ResponseEntity.ok(feeservice.getEnrollmentnoAndSessionName(studentId));
    }
    @GetMapping("/session-wise/history/{studentId}")
    public ResponseEntity<?> getSessionWiseHistory(@PathVariable("studentId")Long studentId){

        return ResponseEntity.ok(feeservice.getSessionbasedfees(studentId));
    }

    @GetMapping("/invoice/history/{startdate}/{endDate}")
    public ResponseEntity<?> getInvoicehistory(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId, @PathVariable("startdate") String startdate, @PathVariable("endDate") String endDate){
        String session = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (session == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long sessionId = Long.parseLong(session);
        return ResponseEntity.ok(feeservice.getSessionInvoice(sessionId, startdate, endDate));
    }

    @GetMapping("/invoice/summary")
    public ResponseEntity<?> getInvoicesummary(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId)
    {
        String session = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (session == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long sessionId = Long.parseLong(session);
        return ResponseEntity.ok(feeservice.getfeecollections(sessionId));
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/update/fees/{studentId}/{discountamount}")
    public ResponseEntity<?> updatefeesbydisamount(@PathVariable("studentId") Long studentId, @PathVariable("discountamount") Long disamount, @CookieValue(value = "sessionId", required = false) String cookieSessionId, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId)
    {
        String sessionId = SessionUtil.extractSessionId(cookieSessionId, headerSessionId);
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        Long sessionid = Long.parseLong(sessionId);
        return ResponseEntity.ok(feeservice.setdiscount(studentId, sessionid, disamount));
    }
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<?> getinvoice(@PathVariable("invoiceId")Long invoiceid){
        return ResponseEntity.ok(feeservice.getInvoice(invoiceid));
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/invoice/delete/{invoiceId}")
    public ResponseEntity<?> deleteinvoice(@PathVariable("invoiceId") Long id){
         return ResponseEntity.ok(feeservice.deleteinvoice(id));
    }
    
  
}
