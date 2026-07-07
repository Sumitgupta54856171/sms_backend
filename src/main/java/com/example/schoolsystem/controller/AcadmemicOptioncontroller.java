package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.service.AcademicOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academic-options")
@RequiredArgsConstructor
public class AcadmemicOptioncontroller {

    private final AcademicOperation academicOperation;


    @PostMapping("/time-table/period")
    public ResponseEntity<?> timeTable(@CookieValue("sessionId") String sessionId, @RequestBody TimetableRecord timetableRecord){
        System.out.println(timetableRecord.getTeacher_id());
        return ResponseEntity.ok(academicOperation.createTimetableRecord(timetableRecord,sessionId));

    }
    @GetMapping("/time-table/teacher/{teacherId}")
    public ResponseEntity<?> timeTableByTeacher(@CookieValue("sessionId") String sessionId, @RequestParam("teacherId") Long teacherId){
        return ResponseEntity.ok(academicOperation.getTimetableByTeacher(teacherId,Long.parseLong(sessionId)));
    }

    @GetMapping("/time-table/grade/{gradeClass}")
    public ResponseEntity<?> timeTableByClass(@CookieValue("sessionId") String sessionId, @RequestParam("gradeClass") String gradeClass){
        return ResponseEntity.ok(academicOperation.getTimetableByClass(gradeClass,Long.parseLong(sessionId)));
    }
    @GetMapping("/time-table/all")
    public ResponseEntity<?> timeTableAll(@CookieValue("sessionId") String sessionId){
        return ResponseEntity.ok(academicOperation.getAllTimetables(Long.parseLong(sessionId)));
    }
    
    
}
