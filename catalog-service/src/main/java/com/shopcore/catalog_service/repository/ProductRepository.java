package com.shopcore.catalog_service.repository;

import com.shopcore.catalog_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Spring Boot escribirá la consulta automáticamente por nosotros basado en el nombre
    Optional<Product> findBySlug(String slug);
}
