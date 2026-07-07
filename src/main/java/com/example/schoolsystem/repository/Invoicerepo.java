package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Invoicerepo extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByEnrollementSession_EnrollmentId(Long enrollmentId);
}
