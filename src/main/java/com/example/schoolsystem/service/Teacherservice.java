package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.ClassTeacherAssignment;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.entity.TeacherPlan;
import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.repository.ClassTeacherAssignmentrepo;
import com.example.schoolsystem.repository.TeacherPlandrepo;
import com.example.schoolsystem.repository.Teacherrepo;
import com.example.schoolsystem.repository.Userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.schoolsystem.entity.Role.TEACHER;

@Service
@RequiredArgsConstructor
public class Teacherservice {

    private final Teacherrepo teacherrepo;
    private final Userrepo userrepo;
    private final PasswordEncoder passwordEncoder;
    private final ClassTeacherAssignmentrepo classTeacherAssignmentrepo;
    private final TeacherPlandrepo teacherPlandrepo;


    public ResponseEntity<?> saveTeacherData(Teacher teacher) throws Exception{

        System.out.println(teacher.getPassword());
        if(userrepo.existsUserByEmail(teacher.getEmail())){
            return ResponseEntity.ok("Teacher is already register");
        }
        System.out.println("teacher name is present" + teacher.getFullName());
        User user = new User();
        user.setUsername(teacher.getFullName());
        user.setEmail(teacher.getEmail());
        user.setPassword(passwordEncoder.encode(teacher.getPassword()));
        user.setRole(TEACHER);
        userrepo.save(user);
        teacherrepo.save(teacher);


        return ResponseEntity.ok("successfull");
    }

    public List<?> getallTeacherDetail()throws Exception{
        System.out.println(teacherrepo.findAll());
        return teacherrepo.findAll();
    }
    public ResponseEntity<?> updateteacher(Teacher teacherupdate)throws Exception {
        teacherrepo.save(teacherupdate);

        return ResponseEntity.ok("successfully update teacher detail");
    }
    public ResponseEntity<?> changeRole(User user){
        User us = userrepo.findByEmail(user.getEmail());
        us.setEmail(user.getEmail());
        us.setRole(user.getRole());
        us.setPassword(passwordEncoder.encode(user.getPassword()));
        userrepo.save(us);
        return ResponseEntity.ok("successfully changed update the role");
    }
    public ResponseEntity<?> getclassofclassteacher(Long teacherId,Long id){

         ClassTeacherAssignment ClassName = classTeacherAssignmentrepo.findBySessionIdAndTeacher_Id(id,teacherId);
        return ResponseEntity.ok(ClassName.getGradeClass());
    }
    public ResponseEntity<?> saveTeacherPlan(TeacherPlan teacherPlan,Long sessionId,Long teacherId)
    {
        teacherPlan.setSessionId(sessionId);
        teacherPlan.setTeacher(teacherrepo.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher not found")));
        teacherPlandrepo.save(teacherPlan);
        return ResponseEntity.ok("save successfully");
    }
    public ResponseEntity<?> getteacherplanbyteacheId(Long teacherId, LocalDate date){
        List<TeacherPlan> teacher = teacherPlandrepo.findByDateAndTeacher_Id(date,teacherId);

        return ResponseEntity.ok(teacher);
    }
    public ResponseEntity<?> getteacherPlandelet(Long id){
        return ResponseEntity.ok(teacherPlandrepo.findById(id).orElseThrow(() -> {
            new RuntimeException("Data Not found");
            return null;
        }));
    }


    


    }



 




