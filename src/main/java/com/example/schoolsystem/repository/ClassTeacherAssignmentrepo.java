package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ClassTeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassTeacherAssignmentrepo extends JpaRepository<ClassTeacherAssignment, Long> {

    List<ClassTeacherAssignment> findBySessionId(Long sessionId);

    List<ClassTeacherAssignment> findByTeacherId(Long teacherId);

    @Query("SELECT c FROM ClassTeacherAssignment c WHERE c.gradeClass = :gradeClass AND c.sessionId = :sessionId")
    Optional<ClassTeacherAssignment> findByGradeClassAndSessionId(
            @Param("gradeClass") String gradeClass,
            @Param("sessionId") Long sessionId
    );

    @Query("SELECT c FROM ClassTeacherAssignment c WHERE c.teacher.id = :teacherId AND c.sessionId = :sessionId")
    List<ClassTeacherAssignment> findByTeacherIdAndSessionId(
            @Param("teacherId") Long teacherId,
            @Param("sessionId") Long sessionId
    );
    @Query("SELECT c FROM ClassTeacherAssignment c WHERE c.gradeClass = :gradeClass and c.sessionId= :sessionId")
    ClassTeacherAssignment findByGradeClass(String gradeClass,Long sessionId);

    boolean existsByGradeClassAndSessionId(String gradeClass, Long sessionId);
}
