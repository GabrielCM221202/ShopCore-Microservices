package com.shopcore.catalog_service.service.impl;

import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;
import com.shopcore.catalog_service.mapper.ProductMapper;
import com.shopcore.catalog_service.model.Product;
import com.shopcore.catalog_service.repository.ProductRepository;
import com.shopcore.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // Inyectamos el mapper autogenerado

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // 1. DTO a Entidad
        Product product = productMapper.toEntity(request);
        // 2. Guardar
        Product savedProduct = productRepository.save(product);
        // 3. Entidad a DTO
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}