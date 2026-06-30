package com.example.schoolsystem.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequest {


    @NotBlank(message = "Email khali nahi ho sakta")
    @Email(message = "Sahi email address format daalein")
    private String email;

    @NotBlank(message = "Password khali nahi ho sakta")
    private String password;
}
