package com.example.schoolsystem.dto;

import lombok.Data;

@Data
public class StudentClassResponse {
    private String className;
    private String rolleNo;
    private String StudentName;

    private String scholarNo;
    private Long studentId;
    public StudentClassResponse(String className, String rolleNo, String StudentName,   String scholarNo,Long studentId) {
        this.className = className;
        this.rolleNo = rolleNo;
        this.StudentName = StudentName;
        this.scholarNo = scholarNo;
        this.studentId = studentId;
    }


}
