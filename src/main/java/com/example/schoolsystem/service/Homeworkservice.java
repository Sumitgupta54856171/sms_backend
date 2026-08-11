package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.Homework;
import com.example.schoolsystem.repository.Homeworkrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Homeworkservice {

    private final Homeworkrepo homeworkrepo;

    @Value("${homework.upload.dir:uploads/homework}")
    private String uploadDir;


    public ResponseEntity<?> saveHomework(Homework homework){
        homeworkrepo.save(homework);
        return ResponseEntity.ok("save homework successfully");
    }
    public ResponseEntity<?> getHomework(){
        List<Homework> h = homeworkrepo.findAll();
        return ResponseEntity.ok(h);
    }
    public ResponseEntity<?> getHomeworkById(Long id){
        Homework homework = homeworkrepo.findById(id).orElse(null);
        return ResponseEntity.ok(homework);
    }
    public ResponseEntity<?> deleteHomework(Long id){
        homeworkrepo.deleteById(id);
        return ResponseEntity.ok("delete homework successfully");
    }
    public ResponseEntity<?> updateHomework(Homework homework){
        homeworkrepo.save(homework);
        return ResponseEntity.ok("update homework successfully");
    }

    public ResponseEntity<?> saveHomeworkWithFile(MultipartFile file, Homework homework) throws IOException {
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required and cannot be empty");
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

            // Set file metadata in homework entity
            homework.setFileName(originalFilename);
            homework.setFilePath(filePath.toString());

            // Save to database
            Homework saved = homeworkrepo.save(homework);

            return ResponseEntity.ok(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }

}
