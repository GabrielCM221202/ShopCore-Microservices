package com.shopcore.catalog_service.mapper;

import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;
import com.shopcore.catalog_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// componentModel = "spring" permite inyectarlo como un @Bean normal
@Mapper(componentModel = "spring")
public interface ProductMapper {

    // El Request no trae ID ni estado activo, se lo enseñamos a MapStruct:
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product entity);
}