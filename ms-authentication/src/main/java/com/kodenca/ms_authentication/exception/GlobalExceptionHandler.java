package com.kodenca.ms_authentication.exception;

import com.kodenca.ms_authentication.dto.response.BusinessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessResponse<Void>> handleUnexpectedException(Exception ex) {
        log.error("Unexpected error occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BusinessResponse.<Void>builder()
                        .success(false)
                        .message("Internal server error")
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BusinessResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );
        log.warn("Validation failed: {}", errors);
        return ResponseEntity.badRequest()
                .body(
                        BusinessResponse.<Map<String, String>>builder()
                                .success(false)
                                .message("Validation failed")
                                .timestamp(LocalDateTime.now())
                                .data(errors)
                                .build()
                );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<BusinessResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BusinessResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BusinessResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BusinessResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<BusinessResponse<Void>> handleRoleNotFound(RoleNotFoundException ex) {
        log.error("Role not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BusinessResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .data(null)
                        .build());
    }
}
