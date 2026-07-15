package com.example.schoolsystem.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PhotoResponseDto {

    private MultipartFile photo;
    private Long studentId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Long teacherId;
}
