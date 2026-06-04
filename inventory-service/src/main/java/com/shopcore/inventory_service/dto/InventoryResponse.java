package com.shopcore.inventory_service.dto;

public record InventoryResponse(
        String slug,
        boolean isInStock,
        Integer quantity
) {
}