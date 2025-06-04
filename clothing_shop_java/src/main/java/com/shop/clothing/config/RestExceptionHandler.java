package com.shop.clothing.config;

import com.shop.clothing.common.BusinessLogicException;
import com.shop.clothing.common.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@AllArgsConstructor
public class RestExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        var errorBuilder = ErrorResponse.builder().error("Lỗi đầu vào").httpStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().
                forEach(fieldError -> {
                    if (errors.containsKey(fieldError.getField())) {
                        errors.put(fieldError.getField(), errors.get(fieldError.getField()) + ", " + fieldError.getDefaultMessage());
                    } else {
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                });

        errorBuilder.errors(errors);
        ErrorResponse error = errorBuilder.build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessLogicException.class)

    public ResponseEntity<?> businessLogicExceptionHandler(BusinessLogicException ex) {

        ErrorResponse error = ErrorResponse.builder().error(ex.getMessage()).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)

    public ResponseEntity<?> businessLogicExceptionHandler(ResponseStatusException ex) {

        ErrorResponse error = ErrorResponse.builder().error(ex.getReason()).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        ErrorResponse error = ErrorResponse.builder().error(ex.getMessage()).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
