package com.shopcore.catalog_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @NotBlank(message = "El slug es obligatorio para la URL")
    private String slug;

    @NotBlank(message = "El categoryId es obligatorio")
    private String categoryId;

    @NotNull(message = "El precio base es obligatorio")
    @Min(value = 0, message = "El precio base debe ser mayor o igual a 0")
    private BigDecimal basePrice;

    private List<String> images;

    @NotNull(message = "Los atributos dinámicos no pueden ser nulos")
    private Map<String, Object> attributes;
}