package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.TimetableRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Timetablerepo extends JpaRepository<TimetableRecord, Long> {

    List<TimetableRecord> findBySessionSessionId(Long sessionId);

    List<TimetableRecord> findByGradeClassAndSessionSessionId(String gradeClass, Long sessionId);

    List<TimetableRecord> findByTeacherIdAndSessionSessionId(Long teacherId, Long sessionId);

    @Query("SELECT t FROM TimetableRecord t WHERE t.gradeClass = :gradeClass AND t.periodNumber = :periodNumber AND t.session.sessionId = :sessionId")
    Optional<TimetableRecord> findByGradeClassAndPeriodNumberAndSessionId(
            @Param("gradeClass") String gradeClass,
            @Param("periodNumber") Integer periodNumber,
            @Param("sessionId") Long sessionId
    );

    @Query("SELECT t FROM TimetableRecord t WHERE t.teacher.id = :teacherId AND t.periodNumber = :periodNumber AND t.session.sessionId = :sessionId")
    Optional<TimetableRecord> findByTeacherIdAndPeriodNumberAndSessionId(
            @Param("teacherId") Long teacherId,
            @Param("periodNumber") Integer periodNumber,
            @Param("sessionId") Long sessionId
    );

    TimetableRecord findBySubjectNameAndGradeClass(String subjectName, String gradeClass);
}
