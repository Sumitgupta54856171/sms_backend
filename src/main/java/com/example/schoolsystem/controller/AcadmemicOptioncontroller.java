package com.example.schoolsystem.controller;


import com.example.schoolsystem.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import com.example.schoolsystem.entity.TimetableRecord;
import com.example.schoolsystem.repository.Timetablerepo;
import com.example.schoolsystem.service.AcademicOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

@RestController
@RequestMapping("/api/v1/academic-options")
@RequiredArgsConstructor
public class AcadmemicOptioncontroller {

    private final AcademicOperation academicOperation;
    private final Timetablerepo timetablerepo;


    @PostMapping("/time-table/period")
    public ResponseEntity<?> timeTable(HttpServletRequest request, @RequestBody TimetableRecord timetableRecord){
        String sessionId = SessionUtil.getSessionId(request);
        System.out.println(timetableRecord.getTeacher_id());
        return ResponseEntity.ok(academicOperation.createTimetableRecord(timetableRecord,sessionId));

    }
    @GetMapping("/time-table/teacher/{teacherId}")
    public ResponseEntity<?> timeTableByTeacher(HttpServletRequest request, @PathVariable("teacherId") Long teacherId){
        String sessionId = SessionUtil.getSessionId(request);
        return ResponseEntity.ok(academicOperation.getTimetableByTeacher(teacherId,Long.parseLong(sessionId)));
    }

    @GetMapping("/time-table/grade/{gradeClass}")
    public ResponseEntity<?> timeTableByClass(HttpServletRequest request, @RequestParam("gradeClass") String gradeClass){
        String sessionId = SessionUtil.getSessionId(request);
        return ResponseEntity.ok(academicOperation.getTimetableByClass(gradeClass,Long.parseLong(sessionId)));
    }
    @GetMapping("/time-table/all")
    public ResponseEntity<?> timeTableAll(HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        return ResponseEntity.ok(academicOperation.getAllTimetables(Long.parseLong(sessionId)));
    }
    @PutMapping("/time-table/update/{timetableId}")
    public ResponseEntity<?> updatetimetable(TimetableRecord timetableid){
        System.out.println("start update");
        return ResponseEntity.ok(academicOperation.updateperiodtimetable(timetableid));
    }
    @DeleteMapping("/delete/period/{periodid}")
    public ResponseEntity<?> deleteperiod(@PathVariable("periodid")Long periodid){
        return ResponseEntity.ok(academicOperation.deletperiod(periodid));
    }
    @GetMapping("/timetable/class-teachers/all")
    public ResponseEntity<?> getClassTeacher(HttpServletRequest request){
        String session = SessionUtil.getSessionId(request);
        Long sessionId = Long.parseLong(session);
        return ResponseEntity.ok(academicOperation.getallClassteacher(sessionId));
    }

}
