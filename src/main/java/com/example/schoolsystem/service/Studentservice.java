package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Student;
import com.example.schoolsystem.repository.Studentrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Studentservice {

    private final Studentrepo studentrepo;


    public ResponseEntity<?> saveStudentData(Student student){

        return ResponseEntity.ok("Student is successfully save");
    }

    public ResponseEntity<?> fetchStudentData(){
        return ResponseEntity.ok("succesfull");
    }
}
