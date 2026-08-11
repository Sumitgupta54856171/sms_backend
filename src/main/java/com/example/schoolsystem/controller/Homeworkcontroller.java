package com.example.schoolsystem.controller;

import com.example.schoolsystem.entity.Homework;
import com.example.schoolsystem.service.Homeworkservice;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/homework")
@RequiredArgsConstructor
public class Homeworkcontroller {

    private final Homeworkservice homeworkservice;


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveHomework(@RequestPart("file") MultipartFile file, @RequestPart("homework") Homework homework) throws IOException {
        return ResponseEntity.ok(homeworkservice.saveHomeworkWithFile(file, homework));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getHomework(){
        return ResponseEntity.ok(homeworkservice.getHomework());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHomeworkById(@PathVariable Long id){
        return ResponseEntity.ok(homeworkservice.getHomeworkById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHomework(@PathVariable Long id){
        return ResponseEntity.ok(homeworkservice.deleteHomework(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateHomework(@RequestBody Homework homework){
        return ResponseEntity.ok(homeworkservice.updateHomework(homework));
    }
}
