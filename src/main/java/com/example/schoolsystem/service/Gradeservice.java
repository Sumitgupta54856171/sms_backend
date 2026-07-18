package com.example.schoolsystem.service;

import com.example.schoolsystem.entity.ExanGrade;
import com.example.schoolsystem.entity.TestGrade;
import com.example.schoolsystem.entity.TestTimetable;
import com.example.schoolsystem.repository.Examgraderepo;
import com.example.schoolsystem.repository.TesTgraderepo;
import com.example.schoolsystem.repository.TestTimeTablerepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Gradeservice {

    private TestTimeTablerepo testTimeTablerepo;
    private final TesTgraderepo tesTgraderepo;
    private final Examgraderepo examgraderepo;

    public ResponseEntity<?> gettesttotalmark(String testName){

        return ResponseEntity.ok("fetch data successfully");
    }
    public ResponseEntity<?> getgradefillbyteacherId(Long teacherId,Long examid,Long sessionId,String type){
       if(type.equals("test")){
           List<TestTimetable> l = testTimeTablerepo.findAllBySessionIdAndTeacher_IdAndTesttimetableId(sessionId,teacherId,examid);
           return ResponseEntity.ok(l);
       }else{
           return ResponseEntity.ok("ok");
       }

    }
    public ResponseEntity<?> savemark(List<TestGrade> testGrade){
        for (TestGrade grade : testGrade) {
            System.out.println("check the test id present "+grade.getTestTimetable());
            // Check if a record already exists for this student and testtimetable
            TestGrade existingGrade = null;
            if (grade.getTestTimetable() != null) {
                existingGrade = tesTgraderepo.findByStudentIdAndTestTimetable_TesttimetableId(
                    grade.getStudentId(), 
                    grade.getTestTimetable().getTesttimetableId()
                );
            }
            if (existingGrade != null) {
                // Update existing record
                existingGrade.setMark(grade.getMark());
                existingGrade.setSubject(grade.getSubject());
                existingGrade.setTeacherId(grade.getTeacherId());
                existingGrade.setSessionId(grade.getSessionId());
                existingGrade.setClassNo(grade.getClassNo());
                tesTgraderepo.save(existingGrade);
            } else {
                // Save new record
                tesTgraderepo.save(grade);
            }
        }
        return ResponseEntity.ok("successfully save the student mark");
    }

    public ResponseEntity<?> saveexammark(List<ExanGrade> examGrade){
        for (ExanGrade grade : examGrade) {
            System.out.println("check the testtimetable id ");
            // Check if a record already exists for this student and examtimetable
            ExanGrade existingGrade = null;
            if (grade.getExamtimetableId() != null) {
                existingGrade = examgraderepo.findByStudentIdAndExamtimetableId_TesttimetableId(
                    grade.getStudentId(), 
                    grade.getExamtimetableId().getTesttimetableId()
                );
            }
            if (existingGrade != null) {
                // Update existing record
                existingGrade.setMark(grade.getMark());
                existingGrade.setSubject(grade.getSubject());
                existingGrade.setTeacherId(grade.getTeacherId());
                existingGrade.setSessionId(grade.getSessionId());
                existingGrade.setClassNo(grade.getClassNo());
                examgraderepo.save(existingGrade);
            } else {
                // Save new record
                examgraderepo.save(grade);
            }
        }
        return ResponseEntity.ok("successfully save the student mark");
    }
    public ResponseEntity<?> getgrade(Long session,Long teacherId,String subject,Long examId,String type,String grade){
        System.out.println(type+" check type of test or exam");
        if(type.equals("test")){
            System.out.println("check the test mark is ok ");
            System.out.println(examId);
            List<TestGrade> tg = tesTgraderepo.findAllBySessionIdAndSubjectAndTeacherIdAndClassNo(session,subject,teacherId,grade);
            if(tg.isEmpty()){
                return ResponseEntity.ok("No data found");
            }
            return ResponseEntity.ok(tg);
        }
        List<ExanGrade> eg = examgraderepo.findAllBySessionIdAndSubjectAndTeacherIdAndClassNo(session,subject,teacherId,grade);
        if(eg.isEmpty()){
            return ResponseEntity.ok("No data found");
        }
        return ResponseEntity.ok(eg);


    }




}
