package com.example.schoolsystem.exception;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private boolean success;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> validationErrors; // Sirf validation ke liye

    // Constructors
    public ErrorResponse(boolean success, String message, String errorCode, String path) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    // Validation errors ke liye
    public ErrorResponse(boolean success, String message, String errorCode,
                         String path, Map<String, String> validationErrors) {
        this(success, message, errorCode, path);
        this.validationErrors = validationErrors;
    }


}
