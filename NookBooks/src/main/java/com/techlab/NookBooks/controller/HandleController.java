package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.exception.*;
import com.techlab.NookBooks.model.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.techlab.demo")
public class HandleController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientStockException(NotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler(NullException.class)
    public ResponseEntity<ErrorResponseDTO> handleNullException(NotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidEditException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEditException(NotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler(CheckedDataException.class)
    public ResponseEntity<ErrorResponseDTO> handleCheckedException(NotFoundException e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> hadlerUnknowException(Exception e) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}