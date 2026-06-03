package com.shopcore.catalog_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Petición rechazada por regla de negocio: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Petición Inválida");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/bad-request"));

        // Agregamos un objeto más estructurado para el frontend
        problemDetail.setProperty("errors", List.of(
                Map.of("field", "general", "message", ex.getMessage())
        ));

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        // Convertimos cada error en un objeto con campo y mensaje
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        log.warn("Petición rechazada por validación de datos: {}", errors);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Errores de validación detectados");
        problemDetail.setTitle("Datos Inválidos");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/validation"));
        problemDetail.setProperty("errors", errors); // Ahora es un array de objetos

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJsonParseError(HttpMessageNotReadableException ex) {
        log.error("Error al parsear JSON: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "JSON mal formado o vacío");
        problemDetail.setTitle("Error de formato");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/json"));
        problemDetail.setProperty("errors", List.of(
                Map.of("field", "body", "message", "El JSON enviado está vacío o tiene un formato incorrecto")
        ));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        log.error("Excepción genérica capturada: {}", ex.getClass().getName());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado");
        problemDetail.setTitle("Error Interno");
        problemDetail.setType(URI.create("https://api.shopcore.com/errors/internal"));
        problemDetail.setProperty("errors", List.of(
                Map.of("field", "general", "message", ex.getMessage())
        ));

        return problemDetail;
    }
}
