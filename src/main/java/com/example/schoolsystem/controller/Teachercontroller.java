package com.example.schoolsystem.controller;


import com.example.schoolsystem.entity.Photo;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.entity.TeacherPlan;
import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.service.PhotoService;
import com.example.schoolsystem.service.Teacherservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class Teachercontroller {

    private final Teacherservice teacherservice;
    private final PhotoService photoService;


    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> saveTeacher(@RequestBody Teacher teacher) throws Exception {
        return teacherservice.saveTeacherData(teacher);
    }

    @GetMapping("/all")
    public List<?> getallTeacherdata() throws Exception {
        System.out.println("data fetching start");

        return teacherservice.getallTeacherDetail();
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping ("/change-role")
    public ResponseEntity<?> changeRole(@RequestBody User user) throws Exception {
        return teacherservice.changeRole(user);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateTeacher(@RequestBody Teacher teacher) throws Exception {
        return teacherservice.updateteacher(teacher);
    }
    @GetMapping("/get/teacher/class")
    public ResponseEntity<?> getteacherclass(@CookieValue("teacherId") String te,@CookieValue("sessionId") String  sessionId)
    {
        Long id  = Long.parseLong(sessionId);
        Long teacherId = Long.parseLong(te);
        return teacherservice.getclassofclassteacher(teacherId,id);
    }
    @PostMapping("/teacher/plan/save")
    public ResponseEntity<?> saveTeacherPlan(@RequestBody TeacherPlan teacherPlan,@CookieValue("sessionId")String session,@CookieValue("teacherId") String teacer){
        Long id = Long.parseLong(session);
        Long id1 = Long.parseLong(teacer);
        return ResponseEntity.ok(teacherservice.saveTeacherPlan(teacherPlan,id,id1));

    }
    @GetMapping("/plan/{date}/{teacherId}")
    public ResponseEntity<?> getPlanbyTeacherid(@PathVariable("date")LocalDate date,@PathVariable("teacherId") Long teacherId){
        return ResponseEntity.ok(teacherservice.getteacherplanbyteacheId(teacherId,date));
    }
    
    @PostMapping(value ="/photo/upload",consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveTeacherPhoto(@ModelAttribute com.example.schoolsystem.dto.PhotoResponseDto photoUploadDto){
        return ResponseEntity.ok(photoService.savePhoto(photoUploadDto));
    }
    
    @GetMapping("/photo/{teacherId}")
    public ResponseEntity<?> getTeacherPhoto(@PathVariable("teacherId") Long teacherId)
    {
       try{
           return photoService.getPhoto(null, teacherId);
       } catch (Exception e) {
           throw new org.springframework.security.core.userdetails.UsernameNotFoundException(e.getMessage());
       }
    }
    
    @DeleteMapping("/photo/delete/{teacherId}")
    public ResponseEntity<?> deleteTeacherPhoto(@PathVariable("teacherId") Long teacherId)
    {
        return ResponseEntity.ok(photoService.deletphot(null, teacherId));
    }
    
    @PutMapping("/update/photo")
    public ResponseEntity<?> updateTeacherPhoto(Photo photo){
        return ResponseEntity.ok(photoService.update(photo));
    }


}
