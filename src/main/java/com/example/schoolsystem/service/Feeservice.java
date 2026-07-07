package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.AnnualFeesResponseDto;
import com.example.schoolsystem.dto.EnrollementNoAndSessionNameResponseDto;
import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Invoice;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Invoicerepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Feeservice {

    private final Enrollementrepo enrollementrepo;
    private final Invoicerepo invoicerepo;
    private final PageableArgumentResolver pageableArgumentResolver;


    public ResponseEntity<?> getStudentTotalFee(Long studentId) {
        System.out.println("student id is " + studentId);

        List<Enrollement_session> st = enrollementrepo.findAllByStudent_Id(studentId);
        System.out.println("student list is " + st.get(0).getTotal_fees());

        List<AnnualFeesResponseDto> fe = st.stream()
                .map(s -> new AnnualFeesResponseDto(s.getTotal_fees()))
                .collect(Collectors.toList());
        System.out.println("fee list is " + fe);

        return ResponseEntity.ok(fe);
    }
    public ResponseEntity<?> generatedInvoice(PaymentRequestDto paymentRequestDto){

        Long scholarId = Long.parseLong(paymentRequestDto.getScholarNo());


        Invoice i = new Invoice();
        i.setEnrollementSession(enrollementrepo.findById(paymentRequestDto.getEnrollmentId()).get());
        i.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        i.setPaymentType(paymentRequestDto.getPaymentType());
        i.setScholar_no(scholarId);
        i.setAmount((long) Math.floor(paymentRequestDto.getAmount()));
        i.setRemarks(paymentRequestDto.getRemarks());
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

    return ResponseEntity.ok("data is fetch");
    }













}
