package com.example.schoolsystem.exception;

import com.example.schoolsystem.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Yeh annotation batata hai ki yeh class poore project ke exceptions handle karegi
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Agar User Database Mein Na Mile
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UsernameNotFoundException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), "USER_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 2. Validation Errors (Jab React se galat data aaye, jaise bina '@' ka email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Sabhi galat fields ko map mein daalna taaki frontend har field ke neeche error dikha sake
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 3. Global Fallback (Koi aisi error jo humne sochi nahi thi, jaise NullPointerException)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        ApiResponse response = new ApiResponse(
                false,
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse response = new ApiResponse(false, "Email ya Password galat hai. Kripya dubara check karein.", "INVALID_CREDENTIALS");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}