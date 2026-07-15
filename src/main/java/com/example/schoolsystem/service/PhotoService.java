package com.example.schoolsystem.service;

import com.example.schoolsystem.dto.PhotoResponseDto;
import com.example.schoolsystem.entity.Photo;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.repository.PhotorRepo;
import com.example.schoolsystem.repository.Studentrepo;
import com.example.schoolsystem.repository.Teacherrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final Teacherrepo teacherrepo;

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

        Long teacherId = dto.getTeacherId();
        
        // Ensure at least one ID is provided
        if (studentId == null && teacherId == null) {
            throw new IllegalArgumentException("Either studentId or teacherId must be provided");
        }
        
        Teacher teacher = null;
        if (teacherId != null) {
            teacher = teacherrepo.findById(teacherId).orElse(null);
        }

        // Validate student exists
        com.example.schoolsystem.entity.Student student = null;
        if (studentId != null) {
            student = studentrepo.findById(studentId).orElse(null);
        }

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
                    .teacher(teacher)
                    .build();

            Photo saved = photorRepo.save(photo);

            // Return response DTO
           if(studentId != null){
            PhotoResponseDto responseDto = PhotoResponseDto.builder()
                    .fileName(saved.getFileName())
                    .contentType(saved.getContentType())
                    .fileSize(saved.getFileSize())
                    .studentId(saved.getStudent().getId())
                    .build();
                    return responseDto;
            
           }else{
            PhotoResponseDto responseDto = PhotoResponseDto.builder()
                    .fileName(saved.getFileName())
                    .contentType(saved.getContentType())
                    .fileSize(saved.getFileSize())
                    .teacherId(saved.getTeacher().getId())
                    .build();
               return responseDto;
           }
            
            



        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
    @Transactional
    public ResponseEntity<?> deletphot(Long studentId, Long teacherId){
        System.out.println("delete photo start");
        if (studentId != null) {
            photorRepo.deleteByStudent_Id(studentId);
        } else if (teacherId != null) {
            photorRepo.deleteByTeacher_Id(teacherId);
        } else {
            throw new IllegalArgumentException("Either studentId or teacherId must be provided");
        }
        return ResponseEntity.ok("delete photo");
    }
    
    public ResponseEntity<?> getPhoto(Long studentId, Long teacherId) {
        if (studentId != null) {
            return ResponseEntity.ok(photorRepo.findByStudent_Id(studentId).orElse(null));
        } else if (teacherId != null) {
            return ResponseEntity.ok(photorRepo.findByTeacher_Id(teacherId).orElse(null));
        } else {
            throw new IllegalArgumentException("Either studentId or teacherId must be provided");
        }
    }
    
    @Transactional
    public ResponseEntity<?> update(Photo photo){
        if (photo.getStudent() != null && photo.getStudent().getId() != null) {
            photorRepo.deleteByStudent_Id(photo.getStudent().getId());
        } else if (photo.getTeacher() != null && photo.getTeacher().getId() != null) {
            photorRepo.deleteByTeacher_Id(photo.getTeacher().getId());
        }
        photorRepo.save(photo);
        return ResponseEntity.ok("successfully update");
    }
}