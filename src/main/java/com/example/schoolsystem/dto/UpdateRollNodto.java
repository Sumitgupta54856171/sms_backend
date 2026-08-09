package com.example.schoolsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UpdateRollNodto {
    private Long studentId;
    private String rollNo;
}
