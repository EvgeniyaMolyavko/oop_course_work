package ua.opnu.labwork2.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                String.format("%s: %s", request.getMethod(), request.getRequestURI()),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponseDto> handleDuplicateResourceException( DuplicateResourceException ex,
                                                                            HttpServletRequest request) {
        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                String.format("%s: %s", request.getMethod(), request.getRequestURI()),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponseDto> handleBadRequestException( BadRequestException ex,
                                                                                 HttpServletRequest request) {
        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                String.format("%s: %s", request.getMethod(), request.getRequestURI()),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ApiErrorResponseDto> handleDatabaseOperationException(DatabaseOperationException ex,
                                                                                HttpServletRequest request) {
        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                String.format("%s: %s", request.getMethod(), request.getRequestURI()),
                null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



    @ExceptionHandler(ConflictOperationException.class)
    public ResponseEntity<ApiErrorResponseDto> handleConflictOperationException( ConflictOperationException ex,
                                                                          HttpServletRequest request) {
        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                String.format("%s: %s", request.getMethod(), request.getRequestURI()),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        ApiErrorResponseDto response = new ApiErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }
}

