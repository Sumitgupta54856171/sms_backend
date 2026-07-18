package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.TestGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TesTgraderepo extends JpaRepository<TestGrade,Long> {


    List<TestGrade> findAllBySessionIdAndSubjectAndTeacherIdAndClassNo(Long sessionId, String subject, Long teacherId, String classNo);

    TestGrade findByStudentIdAndTestTimetable_TesttimetableId(Long studentId, Long testTimetableTesttimetableId);


}
