package com.example.schoolsystem.controller;

import com.example.schoolsystem.entity.Notice;
import com.example.schoolsystem.service.Noticeservice;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class Noticecontroller {

    private final Noticeservice noticeService;


    @PostMapping("/save")
    public ResponseEntity<?> saveNotice(@RequestBody Notice notice){
        return ResponseEntity.ok(noticeService.saveNotice(notice));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getNotice(){
        return ResponseEntity.ok(noticeService.getNotice());
    }
}
