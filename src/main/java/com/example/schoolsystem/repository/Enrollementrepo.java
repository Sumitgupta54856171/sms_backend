package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.entity.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Enrollementrepo extends JpaRepository<Enrollement_session,Long> {

    boolean existsByStudent_Id(Long id);



    /*@Query(value = "SELECT * FROM enrollment_session WHERE student_id = :studentId AND session_id_in_enrollment = :sessionId", nativeQuery = true)
    List<Enrollement_session> findAllByStudent_IdAndSession_Id(
            @Param("studentId") Long studentId,
            @Param("sessionId") Long sessionId
    );

    @Query(value = "SELECT * FROM enrollment_session  WHERE student_id= :studentId AND session_id_in_enrollment= :sessionId", nativeQuery = true)
    Enrollement_session findByStudent_IdAndSession_Id(
            @Param("studentId") Long studentId,
            @Param("sessionId") Long sessionId
    );*/




    boolean existsByStudentAndSession_SessionId(Student student, Long sessionSessionId);


    Enrollement_session findByStudent_IdAndSession_SessionId(Long studentId, Long sessionId);


    List<Enrollement_session> findAllByStudent_IdAndSession_SessionId(Long studentId, Long sessionId);


    List<Enrollement_session> findAllByStudent_Id(Long studentId);

    @Query("SELECT e FROM Enrollement_session e WHERE e.class_no = :class_no AND e.session.sessionId = :sessionId")
    List<Enrollement_session> findAllByClass_noAndSession_SessionId(@Param("class_no") String class_no, @Param("sessionId") Long sessionId);

    List<Enrollement_session> findAllBySession_SessionId(Long sessionSessionId);

    Long session(Session session);



}
