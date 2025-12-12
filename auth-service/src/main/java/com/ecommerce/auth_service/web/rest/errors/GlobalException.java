package com.ecommerce.auth_service.web.rest.errors;

import com.ecommerce.auth_service.dto.error.ErrorVM;
// Bạn phải import các exception custom của mình vào đây
// import com.ecommerce.auth_service.exception.ResourceNotFoundException;
// import com.ecommerce.auth_service.exception.EmailAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException; // Import này rất quan trọng
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorVM> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ErrorVM errorResponse = new ErrorVM(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors,
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorVM> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorVM error = new ErrorVM(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorVM> handleEmailExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        ErrorVM error = new ErrorVM(
                HttpStatus.CONFLICT.value(),
                "Resource Conflict",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorVM> handleJsonParseException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorVM error = new ErrorVM(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Dữ liệu JSON gửi lên không đúng định dạng hoặc sai kiểu dữ liệu",
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorVM> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ErrorVM error = new ErrorVM(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "Đường dẫn API không tồn tại",
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorVM> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErrorVM error = new ErrorVM(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> handleGlobalException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        ErrorVM error = new ErrorVM(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}