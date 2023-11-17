package com.sparta.nbcamptodo.exception;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<GlobalResponseDto> duplicateUsername(DuplicateUsernameException e) {
        return ResponseEntity.badRequest().body(new GlobalResponseDto("duplicateUsername", e.getMessage()));
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<GlobalResponseDto> userValidationException(UserValidationException e) {
        return ResponseEntity.badRequest().body(new GlobalResponseDto("userValidationException", e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponseDto> notFoundException(NotFoundException e) {
        return ResponseEntity.badRequest().body(new GlobalResponseDto("notFound", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto> validationException(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();
        e.getAllErrors().forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new GlobalResponseDto("valdaition errors", errors));
    }

}
