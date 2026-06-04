package com.shopcore.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
        @NotBlank(message = "El slug es obligatorio")
        String slug,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
        Integer quantity
) {
}