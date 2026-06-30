package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.service.Teacherservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class Teachercontroller {

    private final Teacherservice teacherservice;


    @PostMapping("/save")
    public ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) throws Exception {
        return teacherservice.saveTeacherData(teacher);
    }

    @GetMapping("/all")
    public List<?> getallTeacherdata() throws Exception {
        System.out.println("data fetching start");

        return teacherservice.getallTeacherDetail();
    }
}
