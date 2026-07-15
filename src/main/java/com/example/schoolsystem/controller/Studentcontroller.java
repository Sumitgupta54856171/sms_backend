package com.example.schoolsystem.controller;


import com.example.schoolsystem.dto.BankDetailsRequestDto;
import com.example.schoolsystem.dto.EnrollmentRequestDto;
import com.example.schoolsystem.dto.PhotoResponseDto;
import com.example.schoolsystem.dto.Studentdto;
import com.example.schoolsystem.entity.BankDetail;
import com.example.schoolsystem.entity.Photo;
import com.example.schoolsystem.entity.Student;
import com.example.schoolsystem.repository.BankRepo;
import com.example.schoolsystem.repository.Studentrepo;
import com.example.schoolsystem.service.PhotoService;
import com.example.schoolsystem.service.Studentservice;
import com.example.schoolsystem.service.StudetnOpertionservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class Studentcontroller {


    private final Studentservice studentservice;
    private final PhotoService photoService;
    private final BankRepo bankRepo;
    private final StudetnOpertionservice studetnOpertionservice;
    private final Studentrepo studentrepo;


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
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
           return photoService.getPhoto(studentId, null);
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
    @GetMapping("/class/v1/{classno}")
    public ResponseEntity<?> getStudentByClass(@PathVariable("classno") String classno,@CookieValue("sessionId")String sessionid){
        System.out.println("class no is "+classno);
        Long sessionId = Long.parseLong(sessionid);
        return studentservice.getStudentbyclass(classno,sessionId);
    }

    @GetMapping("/class/{class}")
    public ResponseEntity<?> getStudentByClassAndSessionId(@PathVariable("class") String class_no, @CookieValue(value = "sessionId", required = false) String sessionId){
        Long sessionId1 = Long.parseLong(sessionId);
        return studentservice.getStudentByClassAndSessionId(class_no,sessionId1);
    }

    @PostMapping("/promote")
    public ResponseEntity<?> promoteStudent(@RequestBody List<EnrollmentRequestDto> enrollmentRequestDto, @CookieValue("sessionId") Long sessionId){
        return ResponseEntity.ok(studetnOpertionservice.saveeEnrollment(enrollmentRequestDto,sessionId));
    }

    @PutMapping("/update/student-detail")
    public ResponseEntity<?> updatestudentdetail    (@RequestBody Student student) throws Exception{
        return studentservice.updateStudentdetail(student);
    }
    @PutMapping("/update/student/bank-detail")
    public ResponseEntity<?> updateBacnkdetail(@RequestBody BankDetail bankDetail) throws Exception    {
        return studentservice.updateBankDetail(bankDetail);
    }

    @GetMapping("/studentlist")
    public ResponseEntity<?> getStudentLsit () throws Exception{
        return ResponseEntity.ok(studentservice.getAllStudent());
    }

    @GetMapping("/student-detail/{studentId}")
    public ResponseEntity<?> getStudentdetail(@PathVariable("studentId")Long studentid) throws Exception{
        return ResponseEntity.ok(studentservice.getstudentdetail(studentid));
    }
    @PutMapping("/update/student/photo")
    public ResponseEntity<?> updatestudentphoto(Photo photo){
        return ResponseEntity.ok(photoService.update(photo));
    }

    @DeleteMapping("/photo/delete/{studentId}")
    public ResponseEntity<?> deletephot(@PathVariable("studentId") Long studentId)
    {
        return ResponseEntity.ok(photoService.deletphot(studentId, null));
    }
    @GetMapping("/class/roll/no/{studentid}")
    public  ResponseEntity<?> getclassandrollno(@PathVariable("studentid") Long studentid,@CookieValue("sessionId") String sessionId){
        Long sessionid = Long.parseLong(sessionId);

        return ResponseEntity.ok(studentservice.getclassandrollno(studentid,sessionid));
    }
    



}
