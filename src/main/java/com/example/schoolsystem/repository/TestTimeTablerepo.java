package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.TestTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestTimeTablerepo extends JpaRepository<TestTimetable,Long> {
    boolean existsBySubjectAndTestcodeAndClassNO(String subject, Long testcode, String classNO);

    List<TestTimetable> findBySessionIdAndTimetableNameAndClassNO(Long sessionId, String timetableName, String classNO);

    String findBySessionId(Long sessionId);

    List<TestTimetable> findAllBySessionId(Long sessionId);

    List<TestTimetable> findAllByTimetableName(String timetableName);
}
