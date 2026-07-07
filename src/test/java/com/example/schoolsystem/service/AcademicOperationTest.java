package com.example.schoolsystem.service;

import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Teacherrepo;
import com.example.schoolsystem.repository.Timetablerepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class AcademicOperationTest {

    @Autowired
    private AcademicOperation academicOperation;

    @Autowired
    private Teacherrepo teacherrepo;

    @Autowired
    private Sessionrepo sessionrepo;

    @Test
    public void testCreateTimetableRecord() {
        Teacher teacher = new Teacher();
        teacher.setFullName("Test Teacher");
        teacher.setEmployee_id("EMP001");
        teacher = teacherrepo.save(teacher);

        Session session = new Session();
        session.setSession_name("2024-25");
        session = sessionrepo.save(session);

        TimetableRecord record = new TimetableRecord();
        record.setGradeClass("Grade 1");
        record.setSubjectName("Math");
        record.setPeriodNumber(1);
        record.setTeacher_id(teacher.getId());
        record.setSession_id(session.getSessionId());

        TimetableRecord saved = academicOperation.createTimetableRecord(record, session.getSessionId().toString());
        assertNotNull(saved.getId());
        assertNotNull(saved.getTeacher());
        assertNotNull(saved.getSession());
    }
}
