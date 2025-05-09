package com.example.easyRide.controller.exception;

import com.example.easyRide.dto.info.ErrorBodyInfo;
import com.example.easyRide.dto.info.MessageBodyInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorBodyInfo notFound(Exception e) {

        return ErrorBodyInfo.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorBodyInfo conflict(DataIntegrityViolationException e) {

        return ErrorBodyInfo.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler({ExpiredJwtException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorBodyInfo badRequest(Exception e) {

        if (e instanceof ExpiredJwtException) {
            return ErrorBodyInfo.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(Instant.now())
                    .build();

        } else if (e instanceof MethodArgumentNotValidException validationException) {
            BindingResult result = validationException.getBindingResult();
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ErrorBodyInfo.builder()
                    .message(errors.get("taxIdCode"))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(Instant.now())
                    .build();
        }

        return ErrorBodyInfo.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .build();
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorBodyInfo methodNotAllowed(HttpRequestMethodNotSupportedException e) {

        return ErrorBodyInfo.builder()
                .message(e.getMessage())
                .status(e.getStatusCode().value())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler({ValidationException.class, BeanInstantiationException.class, MalformedJwtException.class, IllegalStateException.class, DecodingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorBodyInfo InternalSeverError(Exception e) {

        return ErrorBodyInfo.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .build();
    }

}
