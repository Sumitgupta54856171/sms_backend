package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.TestTimetable;
import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.repository.Teacherrepo;
import com.example.schoolsystem.repository.TestTimeTablerepo;
import com.example.schoolsystem.repository.Timetablerepo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Testservice {
    private final Teacherrepo teacherrepo;
    private final TestTimeTablerepo testTimeTablerepo;
    private final Timetablerepo timetablerepo;

    public ResponseEntity<?>  savetesttimetable(List<TestTimetable> testTimetableList,Long sessionId){
        

        List<TestTimetable> test = new ArrayList<>();
        int savedCount = 0;
        int skippedCount = 0;
        
        for(TestTimetable ts : testTimetableList){
            TimetableRecord timetableRecord = timetablerepo.findBySubjectNameAndGradeClass(ts.getSubject(), ts.getClassNO());
            System.out.println("check data is ok"+ts.getClassNO()+" "+ts.getSubject()+" "+ts.getTestcode()+" ");

            if(testTimeTablerepo.existsBySubjectAndTestcodeAndClassNO(ts.getSubject(),ts.getTestcode(),ts.getClassNO())){
                System.out.println("Skipped: Record already exists for subject " + ts.getSubject() + ", testcode " + ts.getTestcode() + ", class " + ts.getClassNO());
                skippedCount++;
                continue;
            }
            
            if(timetableRecord != null){
                ts.setTeacher(timetableRecord.getTeacher());
            }
            ts.setSessionId(sessionId);
           
            testTimeTablerepo.save(ts);
            savedCount++;
        }

        if(savedCount == 0){
            return ResponseEntity.badRequest().body("No records saved. All " + skippedCount + " records were skipped (timetable record already exists or duplicate test timetable entry).");
        }

        return ResponseEntity.ok("Test timetable saved successfully. Saved: " + savedCount + " records. Skipped: " + skippedCount + " records.");
        
    }

    public ResponseEntity<?> gettimetable(String classNo,String testName,Long sessionId){
        List<TestTimetable> testTimetableList = testTimeTablerepo.findBySessionIdAndTimetableNameAndClassNO(sessionId,testName,classNo);
        if(testTimetableList.isEmpty()){
            ResponseEntity.ok("test is not present");
        }
        return ResponseEntity.ok(testTimetableList);
    }
    public ResponseEntity<?> getTestName(Long sessionId){
        List<TestTimetable> testTimetables = testTimeTablerepo.findAllBySessionId(sessionId);
        if(testTimetables == null || testTimetables.isEmpty()){
            return ResponseEntity.ok("No test name found for session");
        }
        
        // Extract unique test names
        List<String> uniqueTestNames = testTimetables.stream()
            .map(TestTimetable::getTimetableName)
            .filter(name -> name != null && !name.isEmpty())
            .distinct()
            .toList();
            
        if(uniqueTestNames.isEmpty()){
            return ResponseEntity.ok("No test name found for session");
        }
        
        return ResponseEntity.ok(uniqueTestNames);
    }
    public ResponseEntity<?> getTestByName(String name){
        List<TestTimetable> ttt = testTimeTablerepo.findAllByTimetableName(name);
        if(!ttt.isEmpty()){
            return ResponseEntity.ok(ttt);
        }
        else{
            return  ResponseEntity.ok(0);
        }

    }
    public ResponseEntity<?> deletetimetable(Long id){
        testTimeTablerepo.deleteById(id);
        return ResponseEntity.ok("successfully test time table delete");
    }

}
