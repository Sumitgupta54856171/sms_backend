package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ExanGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Examgraderepo extends JpaRepository<ExanGrade,Long> {


    List<ExanGrade> findAllBySessionIdAndSubjectAndTeacherIdAndClassNo(Long sessionId, String subject, Long teacherId, String classNo);

    Optional<ExanGrade> findByStudentIdAndExamtimetableId(Long studentId, Long examtimetableId);
    List<ExanGrade> findAllBySessionIdAndClassNo(Long sessionId, String classNo);

}
