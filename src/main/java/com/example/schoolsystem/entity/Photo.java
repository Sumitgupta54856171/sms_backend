package com.example.schoolsystem.entity;





import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    private String filePath; // /uploads/photos/uuid-filename.jpg

    private Long fileSize;


    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
