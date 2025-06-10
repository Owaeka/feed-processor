package com.sportygroup.feedprocessor.exception;

import com.sportygroup.feedprocessor.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedMessageException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(UnsupportedMessageException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()

                .timestamp(LocalDateTime.now())
                .error("Bad Request")
                .status(HttpStatus.BAD_REQUEST.value())
                .trace(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
