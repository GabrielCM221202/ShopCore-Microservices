package com.shopcore.catalog_service.controller;

import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;
import com.shopcore.catalog_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Devuelve 201 en lugar de 200
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        log.info("Recibida petición para crear producto: {}", request.getName());
        return productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        log.info("Recibida petición para obtener todos los productos");
        return productService.getAllProducts();
    }
}

