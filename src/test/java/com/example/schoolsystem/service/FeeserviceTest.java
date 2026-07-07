package com.example.schoolsystem.service;

import com.example.schoolsystem.dto.PaymentRequestDto;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Invoice;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Invoicerepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeeserviceTest {

    @Mock
    private Enrollementrepo enrollementrepo;

    @Mock
    private Invoicerepo invoicerepo;

    @InjectMocks
    private Feeservice feeservice;

    @Test
    public void testGeneratedInvoiceSavesOnce() {
        // Arrange
        PaymentRequestDto dto = new PaymentRequestDto();
        dto.setScholarNo("123");
        dto.setEnrollmentId(1L);
        dto.setPaymentMethod("Cash");
        dto.setPaymentType("Tuition");
        dto.setAmount(100.0);
        dto.setRemarks("Paid");

        Enrollement_session enrollment = new Enrollement_session();
        enrollment.setEnrollmentId(1L);

        when(enrollementrepo.findById(1L)).thenReturn(Optional.of(enrollment));
        when(invoicerepo.save(any(Invoice.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ResponseEntity<?> response = feeservice.generatedInvoice(dto);

        // Assert
        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoicerepo, times(1)).save(invoiceCaptor.capture());

        Invoice savedInvoice = invoiceCaptor.getValue();
        assertEquals(123L, savedInvoice.getScholar_no());
        assertEquals("Cash", savedInvoice.getPaymentMethod());
        assertEquals(100L, savedInvoice.getAmount());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedInvoice, response.getBody());
    }
}
