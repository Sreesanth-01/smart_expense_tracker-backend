package com.expensetracker.smart_expense_tracker.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.expensetracker.smart_expense_tracker.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException rex,HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), rex.getMessage(), request.getRequestURI());

        return new  ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException uex, HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), uex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request){
        log.error("Unhandled exception occurred", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", request.getRequestURI() );

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException mex, HttpServletRequest request){
        String message = mex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, request.getRequestURI());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
