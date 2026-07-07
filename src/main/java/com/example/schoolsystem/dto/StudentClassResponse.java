package com.example.schoolsystem.dto;

import lombok.Data;

@Data
public class StudentClassResponse {
    private String className;
    private String rolleNo;
    private String StudentName;

    private String scholarNo;
    public StudentClassResponse(String className, String rolleNo, String StudentName,   String scholarNo) {
        this.className = className;
        this.rolleNo = rolleNo;
        this.StudentName = StudentName;
        this.scholarNo = scholarNo;
    }


}
