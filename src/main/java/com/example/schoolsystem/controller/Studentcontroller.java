package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.BankDetailsRequestDto;
import com.example.schoolsystem.dto.PhotoResponseDto;
import com.example.schoolsystem.dto.Studentdto;
import com.example.schoolsystem.repository.BankRepo;
import com.example.schoolsystem.repository.PhotorRepo;
import com.example.schoolsystem.service.PhotoService;
import com.example.schoolsystem.service.Studentservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.ServiceNotFoundException;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class Studentcontroller {


    private final Studentservice studentservice;
    private final PhotoService photoService;
    private final PhotorRepo photorRepo;
    private final BankRepo bankRepo;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Studentdto studentdto, @CookieValue(value = "sessionId") String id) throws Exception {
        Long sessionId = Long.parseLong(id);
        return studentservice.saveStudentData(studentdto, sessionId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> fetchStudentData(@CookieValue(value = "sessionId", required = false) String sessionId) throws Exception {
        if (sessionId == null) {
            return ResponseEntity.badRequest().body("Session ID is required");
        }
        System.out.println("data fetching start");
        System.out.println(sessionId);
        Long id = Long.parseLong(sessionId);
        return studentservice.fetchStudentData(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) throws Exception{
        return studentservice.deletStudent(id);
    }

    @GetMapping("/class/{class}")
    public ResponseEntity<?> getStudentByClass(@PathVariable("class") String class_no, @CookieValue(value = "sessionId", required = false) String sessionId){
        Long sessionId1 = Long.parseLong(sessionId);
        return studentservice.getStudentByClass(class_no,sessionId1);
    }

    @PostMapping(value ="/photo/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savephot(@ModelAttribute PhotoResponseDto photoUploadDto){
        return ResponseEntity.ok(photoService.savePhoto(photoUploadDto));
    }

    @PostMapping("/bank-details")
    public ResponseEntity<?> saveBankDetail(@RequestBody BankDetailsRequestDto bankDetailsRequestDto)
    {
        return studentservice.saveBankDetail(bankDetailsRequestDto);
    }

    @GetMapping("/photo/{studentId}")
    public ResponseEntity<?> getPhoto(@PathVariable("studentId") Long studentId)
    {
       try{
           return ResponseEntity.ok(photorRepo.findByStudent_Id(studentId));

       } catch (Exception e) {
           throw new UsernameNotFoundException(e.getMessage());
       }
    }

    @GetMapping("/bank-details/{studentId}")
    public ResponseEntity<?> getBankdetail(@PathVariable("studentId") Long studentId)
    {
        try{
            return ResponseEntity.ok(bankRepo.findByStudent_Id(studentId));

        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    @GetMapping("/class/{classno}")
    public ResponseEntity<?> getStudentByClass(@PathVariable("classno") String classno){
        return studentservice.getStudentbyclass(classno);
    }


}
