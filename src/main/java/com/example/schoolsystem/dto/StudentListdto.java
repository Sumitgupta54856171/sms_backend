package com.example.schoolsystem.dto;

import com.example.schoolsystem.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentListdto {
    private String StudentName;
    private Long StudentId;
    private String scholarNo;
    private String faterhName;
    private String motherName;
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentListdto(String studentName, Long studentId, String scholarNo, String faterhName, String motherName, Status status) {
        StudentName = studentName;
        StudentId = studentId;
        this.scholarNo = scholarNo;
        this.faterhName = faterhName;
        this.motherName = motherName;
        this.status = status;
    }
    
}
