package com.example.schoolsystem.dto;


import lombok.Data;

@Data
public class ClassTeacherName {
    private String teacherName;
    public ClassTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
