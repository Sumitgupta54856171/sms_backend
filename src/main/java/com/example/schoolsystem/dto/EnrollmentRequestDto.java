package com.example.schoolsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class EnrollmentRequestDto {
    private String classNo;
    private Long studentId;
    @JsonProperty("Totalfees")
    private Long Totalfees;
    private String rolNo;
}
