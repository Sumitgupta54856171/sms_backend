package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.ExanGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Examgraderepo extends JpaRepository<ExanGrade,Long> {


    List<ExanGrade> findAllBySessionIdAndSubjectAndTeacherIdAndClassNo(Long sessionId, String subject, Long teacherId, String classNo);

    ExanGrade findByStudentIdAndExamtimetableId_TesttimetableId(Long studentId, Long examtimetableIdTesttimetableId);

}
