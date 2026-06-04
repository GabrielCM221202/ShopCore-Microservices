package com.shopcore.catalog_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String slug;

    @Field("category_id")
    private String categoryId;

    @Field("base_price")
    private BigDecimal basePrice;

    private List<String> images;

    // Aquí ocurre la magia del esquema dinámico
    private Map<String, Object> attributes;

    @Field("is_active")
    private boolean isActive;

    private ProductStatus status = ProductStatus.ACTIVE;
}
