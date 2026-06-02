package com.shopcore.catalog_service.dto;

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
    private String name;
    private String slug;
    private String categoryId;
    private BigDecimal basePrice;
    private List<String> images;
    private Map<String, Object> attributes;
    // Fíjate que NO incluimos 'id' ni 'isActive'. El cliente no decide eso.
}