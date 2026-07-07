package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.ClassTeacherAssignment;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.repository.ClassTeacherAssignmentrepo;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Teacherrepo;
import com.example.schoolsystem.repository.Timetablerepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AcademicOperation {

    private final Timetablerepo timetablerepo;
    private final ClassTeacherAssignmentrepo classTeacherAssignmentrepo;
    private final Teacherrepo teacherrepo;

    private final Sessionrepo sessionrepo;


    // Create timetable record
    @Transactional
    public TimetableRecord createTimetableRecord(TimetableRecord timetableRecord,String session_id) {

         Long sessionId = Long.parseLong(session_id);
        
        Teacher teacher = teacherrepo.findById(timetableRecord.getTeacher_id())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Session session = sessionrepo.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        timetableRecord.setTeacher(teacher);
        timetableRecord.setSession(session);

        // Check for conflicts
        Optional<TimetableRecord> existingClassPeriod = timetablerepo.findByGradeClassAndPeriodNumberAndSessionId(
                timetableRecord.getGradeClass(),
                timetableRecord.getPeriodNumber(),
                sessionId
        );
        System.out.println(timetableRecord.getPeriodNumber()+session_id+timetableRecord.getSubjectName()+timetableRecord.getTeacher_id());

        if (existingClassPeriod.isPresent()) {
            throw new RuntimeException("This period is already assigned for this class");
        }

        Optional<TimetableRecord> existingTeacherPeriod = timetablerepo.findByTeacherIdAndPeriodNumberAndSessionId(
                timetableRecord.getTeacher_id(),
                timetableRecord.getPeriodNumber(),
                sessionId
        );

        if (existingTeacherPeriod.isPresent()) {
            throw new RuntimeException("Teacher is already assigned to another class during this period");
        }

        Long teacherId = timetableRecord.getTeacher_id();
        TimetableRecord saved = timetablerepo.save(timetableRecord);

        // Auto-assign class teacher if first period
        if (timetableRecord.getPeriodNumber() == 1) {
            assignClassTeacher(timetableRecord.getGradeClass(), timetableRecord.getTeacher_id(), sessionId);
        }

        return saved;
    }

    // View timetable by class
    public List<TimetableRecord> getTimetableByClass(String gradeClass, Long sessionId) {
        return timetablerepo.findByGradeClassAndSessionSessionId(gradeClass, sessionId);
    }

    // View timetable by teacher
    public List<TimetableRecord> getTimetableByTeacher(Long teacherId, Long sessionId) {
        return timetablerepo.findByTeacherIdAndSessionSessionId(teacherId, sessionId);
    }

    // Assign class teacher
    @Transactional
    public ClassTeacherAssignment assignClassTeacher(String gradeClass, Long teacherId, Long sessionId) {
        // Check if class teacher already exists for this class and session
        Optional<ClassTeacherAssignment> existing = classTeacherAssignmentrepo.findByGradeClassAndSessionId(gradeClass, sessionId);
        
        if (existing.isPresent()) {
            // Update existing assignment
            ClassTeacherAssignment assignment = existing.get();
            Teacher teacher = teacherrepo.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            assignment.setTeacher(teacher);
            return classTeacherAssignmentrepo.save(assignment);
        } else {
            // Create new assignment
            Teacher teacher = teacherrepo.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            
            ClassTeacherAssignment assignment = new ClassTeacherAssignment();
            assignment.setGradeClass(gradeClass);
            assignment.setTeacher(teacher);
            assignment.setSessionId(sessionId);
            
            return classTeacherAssignmentrepo.save(assignment);
        }
    }

    // View class teachers by class
    public List<ClassTeacherAssignment> getClassTeachersByClass(String gradeClass, Long sessionId) {
        return classTeacherAssignmentrepo.findByGradeClassAndSessionId(gradeClass, sessionId)
                .map(List::of)
                .orElse(List.of());
    }

    // View all class teachers for a session
    public List<ClassTeacherAssignment> getAllClassTeachers(Long sessionId) {
        return classTeacherAssignmentrepo.findBySessionId(sessionId);
    }

    // View all timetable records for a session
    public List<TimetableRecord> getAllTimetables(Long sessionId) {
        System.out.println("session id  is "+sessionId);
        return timetablerepo.findBySessionSessionId(sessionId);
    }

    // Delete timetable record
    @Transactional
    public void deleteTimetableRecord(Long id) {
        timetablerepo.deleteById(id);
    }

    // Update timetable record
    @Transactional
    public TimetableRecord updateTimetableRecord(Long id, TimetableRecord timetableRecord) {
        TimetableRecord existing = timetablerepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable record not found"));

        existing.setGradeClass(timetableRecord.getGradeClass());
        existing.setSubjectName(timetableRecord.getSubjectName());
        existing.setPeriodNumber(timetableRecord.getPeriodNumber());
        existing.setTeacher(timetableRecord.getTeacher());
        existing.setSession(timetableRecord.getSession());

        return timetablerepo.save(existing);
    }
}
