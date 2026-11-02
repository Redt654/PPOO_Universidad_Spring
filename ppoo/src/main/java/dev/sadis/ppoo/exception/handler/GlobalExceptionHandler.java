package dev.sadis.ppoo.exception.handler;

import dev.sadis.ppoo.exception.career.CareerCodeNotFoundException;
import dev.sadis.ppoo.exception.career.CareerCodeTakenException;
import dev.sadis.ppoo.exception.career.CareerNameTakenException;
import dev.sadis.ppoo.exception.career.CareerNotFoundException;
import dev.sadis.ppoo.exception.student.DocumentTakenException;
import dev.sadis.ppoo.exception.student.EmailTakenException;
import dev.sadis.ppoo.exception.student.StudentCodeTakenException;
import dev.sadis.ppoo.exception.student.StudentNotFoundException;
import dev.sadis.ppoo.exception.teacher.TeacherEmailTakenException;
import dev.sadis.ppoo.exception.teacher.TeacherNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            TeacherEmailTakenException.class,
            CareerCodeTakenException.class,
            CareerNameTakenException.class,
            DocumentTakenException.class,
            EmailTakenException.class,
            StudentCodeTakenException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflictExceptions(RuntimeException error, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, error.getMessage(), request, null);
    }

    @ExceptionHandler({
            TeacherNotFoundException.class,
            CareerCodeNotFoundException.class,
            CareerNotFoundException.class,
            StudentNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> handleNotFoundExceptions(RuntimeException error, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, error.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException error, HttpServletRequest request) {
        Map<String, String> errors = error.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value",
                        (first, second) -> first
                ));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> dataIntegrityViolation(DataIntegrityViolationException error, HttpServletRequest request) {
        log.warn("Data integrity violation on {}: {}", request.getRequestURI(), error.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Data integrity violation", request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception error, HttpServletRequest request) {
        log.error("Unexpected error occurred on {}", request.getRequestURI(), error);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request, null);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus code, String messageError, HttpServletRequest request, Map<String, String> errors) {
        var response = new ApiErrorResponse(code, messageError, request.getRequestURI(), errors);
        return ResponseEntity.status(code).body(response);
    }
}