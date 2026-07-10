package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Invoice;
import com.example.schoolsystem.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Invoicerepo extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByEnrollementSession_EnrollmentId(Long enrollmentId);

    @Query("SELECT i FROM Invoice i JOIN i.enrollementSession e WHERE e.session.sessionId = :sessionId AND e.student.id = :studentId")
    List<Invoice> findAllByEnrollementSession_SessionIdAndStudent_Id(@Param("sessionId") Long sessionId, @Param("studentId") Long studentId);


    List<Invoice> findAllByEnrollementSession(Enrollement_session enrollementSession);

    List<Invoice> findAllByEnrollementSession_Session(Session enrollementSessionSession);
}
