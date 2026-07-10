package com.example.schoolsystem.dto;



import com.example.schoolsystem.entity.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studentdto {

    // Existing entity fields
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Scholar number is required")
    @Size(min = 5, max = 50)
    private String scholar_no;

    @Size(max = 9)
    private String sssmid;

    @Size(min = 12, max = 12)
    @Pattern(regexp = "\\d{12}")
    private String aadhaar;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dob;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}")
    private String phone;

    @NotBlank(message = "Father's name is required")
    private String father_name;

    @NotBlank(message = "Mother's name is required")
    private String mother_name;

    private Status status;

    // ✅ ADDITIONAL FIELDS (Entity mein nahi hain)
    @NotBlank(message = "Roll number is required")
    private String roll_no;

    @NotBlank(message = "Class is required")
    private String class_no;

    private Long total_fees;
    private String apaarId;
    private String penId;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}