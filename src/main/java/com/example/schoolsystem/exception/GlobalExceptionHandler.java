package com.example.schoolsystem.exception;

import com.example.schoolsystem.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// ✅ Ek hi consistent format har jagah


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ✅ 1. Username Not Found (404)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UsernameNotFoundException ex,
            WebRequest request) {

        logger.warn("User not found: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                false,
                ex.getMessage(),
                "USER_NOT_FOUND",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // ✅ 2. Validation Errors (400) - Same ErrorResponse format mein
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        logger.warn("Validation failed for path: {} | Errors: {}",
                request.getDescription(false), errors);

        ErrorResponse response = new ErrorResponse(
                false,
                "Validation failed. Please check your input.",
                "VALIDATION_ERROR",
                request.getDescription(false).replace("uri=", ""),
                errors  // Validation errors alag field mein
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ✅ 3. Bad Credentials (401)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            WebRequest request) {

        logger.warn("Bad credentials attempt at: {}", request.getDescription(false));

        ErrorResponse response = new ErrorResponse(
                false,
                "Email ya Password galat hai. Kripya dubara check karein.",
                "INVALID_CREDENTIALS",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ✅ 4. Custom Application Exceptions (Agar aapne banaye hain)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {

        logger.error("Runtime error: {}", ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse(
                false,
                ex.getMessage(),
                "RUNTIME_ERROR",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ✅ 5. Global Fallback (500) - Generic message, detailed log
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        // ⚠️ Production mein internal details mat dikhao
        logger.error("Unexpected error at {}: ", request.getDescription(false), ex);

        ErrorResponse response = new ErrorResponse(
                false,
                "Kuch galat ho gaya. Kripya baad mein try karein.", // User-friendly Hindi/English
                "INTERNAL_SERVER_ERROR",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}