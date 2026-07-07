package com.example.schoolsystem.service;

import com.example.schoolsystem.dto.PhotoResponseDto;
import com.example.schoolsystem.entity.Photo;
import com.example.schoolsystem.repository.PhotorRepo;
import com.example.schoolsystem.repository.Studentrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotorRepo photorRepo;
    private final Studentrepo studentrepo;

    @Value("${photo.upload.dir:uploads/photos}")
    private String uploadDir;

    public PhotoResponseDto savePhoto(PhotoResponseDto dto) {
        // Validate file
        MultipartFile file = dto.getPhoto();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required and cannot be empty");
        }

        // Validate studentId
        Long studentId = dto.getStudentId();
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID is required");
        }

        // Validate student exists
        var student = studentrepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        try {
            // Create upload directory
            Path root = Paths.get(uploadDir);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            // Generate safe filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID() + extension;
            Path filePath = root.resolve(fileName);

            // Save file to disk (replace if exists)
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save to database
            Photo photo = Photo.builder()
                    .fileName(fileName)
                    .contentType(file.getContentType())
                    .filePath(filePath.toString())
                    .fileSize(file.getSize())
                    .student(student)
                    .build();

            Photo saved = photorRepo.save(photo);

            // Return response DTO
            PhotoResponseDto responseDto = PhotoResponseDto.builder()
                    .fileName(saved.getFileName())
                    .contentType(saved.getContentType())
                    .fileSize(saved.getFileSize())
                    .studentId(saved.getStudent().getId())
                    .build();

            return responseDto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
}