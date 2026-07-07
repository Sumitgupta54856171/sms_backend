package com.example.schoolsystem.service;

import com.example.schoolsystem.dto.PhotoResponseDto;
import com.example.schoolsystem.entity.Photo;
import com.example.schoolsystem.entity.Student;
import com.example.schoolsystem.repository.PhotorRepo;
import com.example.schoolsystem.repository.Studentrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {

    @Mock
    private PhotorRepo photorRepo;

    @Mock
    private Studentrepo studentrepo;

    @InjectMocks
    private PhotoService photoService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(photoService, "uploadDir", "target/test-uploads");
    }

    @Test
    public void testSavePhotoSuccess() throws IOException {
        // Arrange
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        PhotoResponseDto dto = PhotoResponseDto.builder()
                .studentId(studentId)
                .photo(file)
                .build();

        Student student = new Student();
        student.setId(studentId);

        when(studentrepo.findById(studentId)).thenReturn(Optional.of(student));
        when(photorRepo.save(any(Photo.class))).thenAnswer(i -> {
            Photo p = (Photo) i.getArguments()[0];
            p.setId(100L);
            return p;
        });

        // Act
        PhotoResponseDto response = photoService.savePhoto(dto);

        // Assert
        String savedFileName = response.getFileName();
        org.junit.jupiter.api.Assertions.assertTrue(savedFileName.endsWith(".jpg"));
        assertEquals("image/jpeg", response.getContentType());
        assertEquals(studentId, response.getStudentId());
        verify(photorRepo, times(1)).save(any(Photo.class));
    }

    @Test
    public void testSavePhotoFileNull() {
        // Arrange
        PhotoResponseDto dto = PhotoResponseDto.builder()
                .studentId(1L)
                .photo(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            photoService.savePhoto(dto);
        });
        assertEquals("File is required and cannot be empty", exception.getMessage());
    }

    @Test
    public void testSavePhotoStudentIdNull() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
        PhotoResponseDto dto = PhotoResponseDto.builder()
                .studentId(null)
                .photo(file)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            photoService.savePhoto(dto);
        });
        assertEquals("Student ID is required", exception.getMessage());
    }

    @Test
    public void testSavePhotoStudentNotFound() {
        // Arrange
        Long studentId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "photo",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        PhotoResponseDto dto = PhotoResponseDto.builder()
                .studentId(studentId)
                .photo(file)
                .build();

        when(studentrepo.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            photoService.savePhoto(dto);
        });
        assertEquals("Student not found with ID: 1", exception.getMessage());
    }
}
