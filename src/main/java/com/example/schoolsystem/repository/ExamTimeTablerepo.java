package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ExamTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamTimeTablerepo extends JpaRepository<ExamTimeTable,Long > {
    boolean existsBySubjectAndExamcodeAndClassNO(String subject, Long examcode, String ClassNO);
    
    List<ExamTimeTable> findBySessionIdAndTimetableNameAndClassNO(Long sessionId, String timetableName, String ClassNO);
    
    List<ExamTimeTable> findAllBySessionId(Long sessionId);
    
    List<ExamTimeTable> findAllByTimetableName(String timetableName);
}
