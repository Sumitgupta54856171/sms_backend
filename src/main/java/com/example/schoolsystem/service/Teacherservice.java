package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.repository.Teacherrepo;
import com.example.schoolsystem.repository.Userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.schoolsystem.entity.Role.TEACHER;

@Service
@RequiredArgsConstructor
public class Teacherservice {

    private final Teacherrepo teacherrepo;
    private final Userrepo userrepo;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> saveTeacherData(Teacher teacher) throws Exception{

        System.out.println(teacher.getPassword());
        if(userrepo.existsUserByEmail(teacher.getEmail())){
            return ResponseEntity.ok("Teacher is already register");
        }
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


}
