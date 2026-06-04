package com.shopcore.catalog_service.controller;

import com.shopcore.catalog_service.dto.PageResponse;
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
    public PageResponse<ProductResponse> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Recibida petición para obtener todos los productos. Página: {}, Tamaño: {}", page, size);
        return productService.getAllProducts(page, size, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id){
        log.info("Recibida petición para obtener un producto por su id");
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        log.info("Actualizando producto con ID: {}", id);
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve 204 sin contenido
    public void deleteProduct(@PathVariable String id) {
        log.info("Borrando lógicamente el producto con ID: {}", id);
        productService.deleteProduct(id);
    }

}

