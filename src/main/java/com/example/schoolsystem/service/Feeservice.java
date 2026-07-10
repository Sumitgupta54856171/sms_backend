package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.AnnualFeesResponseDto;
import com.example.schoolsystem.dto.EnrollementNoAndSessionNameResponseDto;
import com.example.schoolsystem.dto.InvoiceResponseDto;
import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.dto.SessionFeesDetailResponse;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Invoice;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Invoicerepo;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Studentrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Feeservice {

    private final Enrollementrepo enrollementrepo;
    private final Invoicerepo invoicerepo;
    private final PageableArgumentResolver pageableArgumentResolver;
    private final Studentrepo studentrepo;
    private final Sessionrepo sessionrepo;


    public ResponseEntity<?> getStudentTotalFee(Long studentId,Long sessionId) {
        System.out.println("student id is " + studentId);

        List<Enrollement_session> st = enrollementrepo.findAllByStudent_IdAndSession_SessionId(
                studentId,sessionId);
        System.out.println("student list is " + st.get(0).getTotal_fees());

        List<AnnualFeesResponseDto> fe = st.stream()
                .map(s -> new AnnualFeesResponseDto(s.getTotal_fees()))
                .collect(Collectors.toList());
        System.out.println("fee list is " + fe);

        return ResponseEntity.ok(fe);
    }
    public ResponseEntity<?> generatedInvoice(PaymentRequestDto paymentRequestDto){

        System.out.println(paymentRequestDto.getClassNo());

        Long scholarId = Long.parseLong(paymentRequestDto.getScholarNo());


        Invoice i = new Invoice();
        i.setEnrollementSession(enrollementrepo.findById(paymentRequestDto.getEnrollmentId()).get());
        i.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        i.setPaymentType(paymentRequestDto.getPaymentType());
        i.setScholar_no(scholarId);
        i.setAmount((long) Math.floor(paymentRequestDto.getAmount()));
        i.setRemarks(paymentRequestDto.getRemarks());
        i.setClass_no(paymentRequestDto.getClassNo().replace("Grade ",""));
        Invoice savedInvoice = invoicerepo.save(i);


        return ResponseEntity.ok(savedInvoice);
    }

    public ResponseEntity<?> getInvoice(Long invoiceId){
        Optional<Invoice> invoice = invoicerepo.findById(invoiceId);
        if(invoice.isPresent()){
            return ResponseEntity.ok(invoice.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getEnrollmentnoAndSessionName(Long studentId)
    {
        List<Enrollement_session> st = enrollementrepo.findAllByStudent_Id(studentId);
        List<EnrollementNoAndSessionNameResponseDto> responseDtos = st.stream().map(s-> new EnrollementNoAndSessionNameResponseDto(s.getEnrollmentId(),s.getSession().getSession_name())).collect(Collectors.toList());

    return ResponseEntity.ok(responseDtos);
    }






    public ResponseEntity<?> getInvoicebyEnrollmentId(Long enrollmentId){

        List<Invoice> in = invoicerepo.findAllByEnrollementSession_EnrollmentId(enrollmentId);
        List<InvoiceResponseDto> response = in.stream().map(s-> new InvoiceResponseDto(s.getInvoice_id(),s.getEnrollementSession().getStudent().getName(),s.getCreatedAt(),s.getPaymentMethod(),s.getAmount()) ).collect(Collectors.toList());


        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<?> getfeeprofiledetail(Long studentId,Long sessionId){
        System.out.println("check the student Id and session Id "+studentId+" "+sessionId);
        Enrollement_session enrollementSession = enrollementrepo.findByStudent_IdAndSession_SessionId(studentId, sessionId);

        System.out.println("student id is " + enrollementSession.getStudentId()+ " check sesion" + " "+ enrollementSession.getSession().getSession_name()+"check the totalamount" + " "+enrollementSession.getTotal_fees());
        List<Invoice> ls = invoicerepo.findAllByEnrollementSession_SessionIdAndStudent_Id(sessionId,studentId);
        Long totalPaid = ls.stream().mapToLong(Invoice::getAmount).sum();
        System.out.println("total paid is " + totalPaid);
        System.out.println("total paid is " + enrollementSession.getTotal_fees());
        Long totalAnnualFee = enrollementSession.getTotal_fees();
        Long totaldue = enrollementSession.getDueFees();
        Long discount = enrollementSession.getDiscountFees();
        Map<String,Long> response = new HashMap<>();
        response.put("totalAnnualFee", totalAnnualFee);
        response.put("totalPaid", totalPaid);
        response.put("totaldue", totaldue);
        response.put("discount", discount);
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> getSessionbasedfees(Long studentid){

        List<Enrollement_session> enrollementSessions = enrollementrepo.findAllByStudent_Id(studentid);

        List<SessionFeesDetailResponse> response = new ArrayList<>();


        for(Enrollement_session en : enrollementSessions) {
            List<Invoice> in = invoicerepo.findAllByEnrollementSession_EnrollmentId(en.getEnrollmentId());

            Long totalPaid = in.stream().mapToLong(Invoice::getAmount).sum();
            Long paymentCount = (long) in.size();
            SessionFeesDetailResponse sessionFeesDetailResponse = new SessionFeesDetailResponse(en.getTotal_fees(),en.getDueFees(),totalPaid,en.getSession().getSession_name(),paymentCount);
            response.add(sessionFeesDetailResponse);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getSessionInvoice(Long sessionId, String start, String end){
        LocalDateTime startDate = LocalDateTime.parse(start + "T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse(end + "T23:59:59");

        List<Enrollement_session> sessionEnrollment = enrollementrepo.findAllBySession_SessionId(sessionId);
        Long totalsessionpaidamount = sessionEnrollment.stream().mapToLong(s->s.getTotal_fees() + s.getDueFees() -s.getDiscountFees()).sum();
        Session es = sessionEnrollment.get(0).getSession();
        List<Invoice> allInvoices = invoicerepo.findAllByEnrollementSession_Session(es);
        Long totalInvoicesAmount = allInvoices.stream().mapToLong(Invoice::getAmount).sum();


        List<Invoice> filteredInvoices = allInvoices.stream()
                .filter(invoice -> invoice.getCreatedAt().isAfter(startDate) && invoice.getCreatedAt().isBefore(endDate))
                .toList();


        List<InvoiceResponseDto> invoiceResponseDtos = filteredInvoices.stream()
                .map(invoice -> new InvoiceResponseDto(
                        invoice.getInvoice_id(),
                        invoice.getEnrollementSession().getStudent().getName(),
                        invoice.getCreatedAt(),
                        invoice.getPaymentMethod(),
                        invoice.getAmount()
                ))
                .toList();

        Long totalAmount = filteredInvoices.stream().mapToLong(Invoice::getAmount).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("invoice", invoiceResponseDtos);
        response.put("totalamount", totalAmount);
        response.put("totalsessionpaidamount", totalsessionpaidamount);
        response.put("totalInvoicesAmount", totalInvoicesAmount);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> getfeecollections(Long sessionId){

        List<Enrollement_session> endlela = enrollementrepo.findAllBySession_SessionId(sessionId);
        Long totalsessionfees = endlela.stream().mapToLong(e -> e.getTotal_fees() + e.getDueFees()).sum();
        List<Invoice> invoices = invoicerepo.findAllByEnrollementSession(sessionrepo.findBysessionId(sessionId));
        Long totalpaid = invoices.stream().mapToLong(Invoice::getAmount).sum();
        Map<String, Long> response = new HashMap<>();
        response.put("totalsessionfees", totalsessionfees);
        response.put("totalpaid", totalpaid);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> setdiscount(Long studentId,Long sessionId,Long disamount){

        System.out.println("they check the amount"+studentId+"  "+sessionId+"  "+disamount);
        Enrollement_session enrollementSession = enrollementrepo.findByStudent_IdAndSession_SessionId(studentId, sessionId);

        if (enrollementSession == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollement not found");
        }
        enrollementSession.setDiscountFees(disamount);
        enrollementrepo.save(enrollementSession);
        return ResponseEntity.ok("update student fees");

    }





}
