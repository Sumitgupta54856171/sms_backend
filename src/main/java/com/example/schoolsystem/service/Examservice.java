package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.repository.ExamTimeTablerepo;
import com.example.schoolsystem.repository.Timetablerepo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Examservice {
    private final ExamTimeTablerepo examTimeTablerepo;
    private final Timetablerepo timetablerepo;

    public ResponseEntity<?> saveexamtimetable(List<ExamTimeTable> examTimeTableList, Long sessionId){
        List<ExamTimeTable> exam = new ArrayList<>();
        int savedCount = 0;
        int skippedCount = 0;
        
        for(ExamTimeTable et : examTimeTableList){
            TimetableRecord timetableRecord = timetablerepo.findBySubjectNameAndGradeClass(et.getSubject(), et.getClassNO());
            System.out.println("check data is ok"+et.getClassNO()+" "+et.getSubject()+" "+et.getExamcode()+" ");

            if(examTimeTablerepo.existsBySubjectAndExamcodeAndClassNO(et.getSubject(), et.getExamcode(), et.getClassNO())){
                System.out.println("Skipped: Record already exists for subject " + et.getSubject() + ", examcode " + et.getExamcode() + ", class " + et.getClassNO());
                skippedCount++;
                continue;
            }
            
            if(timetableRecord != null){
                et.setTeacher(timetableRecord.getTeacher());
            }
            et.setSessionId(sessionId);
           
            examTimeTablerepo.save(et);
            savedCount++;
        }

        if(savedCount == 0){
            return ResponseEntity.badRequest().body("No records saved. All " + skippedCount + " records were skipped (timetable record already exists or duplicate exam timetable entry).");
        }

        return ResponseEntity.ok("Exam timetable saved successfully. Saved: " + savedCount + " records. Skipped: " + skippedCount + " records.");
        
    }

    public ResponseEntity<?> getexamtimetable(String classNo, String examName, Long sessionId){
        List<ExamTimeTable> examTimeTableList = examTimeTablerepo.findBySessionIdAndTimetableNameAndClassNO(sessionId, examName, classNo);
        if(examTimeTableList.isEmpty()){
            ResponseEntity.ok("exam is not present");
        }
        return ResponseEntity.ok(examTimeTableList);
    }
    
    public ResponseEntity<?> getExamName(Long sessionId){
        List<ExamTimeTable> examTimeTables = examTimeTablerepo.findAllBySessionId(sessionId);
        if(examTimeTables == null || examTimeTables.isEmpty()){
            return ResponseEntity.ok("No exam name found for session");
        }
        
        // Extract unique exam names
        List<String> uniqueExamNames = examTimeTables.stream()
            .map(ExamTimeTable::getTimetableName)
            .filter(name -> name != null && !name.isEmpty())
            .distinct()
            .toList();
            
        if(uniqueExamNames.isEmpty()){
            return ResponseEntity.ok("No exam name found for session");
        }
        
        return ResponseEntity.ok(uniqueExamNames);
    }
    
    public ResponseEntity<?> getExamByName(String name){
        List<ExamTimeTable> ett = examTimeTablerepo.findAllByTimetableName(name);
        if(!ett.isEmpty()){
            return ResponseEntity.ok(ett);
        }
        else{
            return ResponseEntity.ok(0);
        }
    }
    public ResponseEntity<?> deleteexmatimetable(Long id){

        examTimeTablerepo.deleteById(id);
        return ResponseEntity.ok("sucessfully delete the exam time table");
    }
}
