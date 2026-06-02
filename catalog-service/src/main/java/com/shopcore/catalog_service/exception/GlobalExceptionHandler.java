package com.shopcore.catalog_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // Este método atrapa cualquier IllegalArgumentException lanzada en el código
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Petición rechazada por regla de negocio: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Petición Inválida");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/bad-request"));

        return problemDetail;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        // Extraemos todos los errores y los unimos en un solo String separados por comas
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Petición rechazada por validación de datos: {}", errors);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Errores de validación detectados");
        problemDetail.setTitle("Datos Inválidos");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/validation"));
        problemDetail.setProperty("invalid_fields", errors); // Agregamos propiedad custom al JSON

        return problemDetail;
    }
}