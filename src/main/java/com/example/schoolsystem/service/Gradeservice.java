package com.example.schoolsystem.service;

import com.example.schoolsystem.entity.TestTimetable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Gradeservice {

    private TestTimetable testTimetable;

    public ResponseEntity<?> gettesttotalmark(String testName){

        return ResponseEntity.ok("fetch data successfully");
    }

}
