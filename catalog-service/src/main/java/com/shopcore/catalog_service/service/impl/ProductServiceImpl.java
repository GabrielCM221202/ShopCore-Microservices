package com.shopcore.catalog_service.service.impl;

import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;
import com.shopcore.catalog_service.model.Product;
import com.shopcore.catalog_service.repository.ProductRepository;
import com.shopcore.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // @RequiredArgsConstructor inyecta esta dependencia automáticamente
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        // 1. Mapeo Manual: DTO -> Entidad
        Product product = new Product();
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setCategoryId(request.getCategoryId());
        product.setBasePrice(request.getBasePrice());
        product.setImages(request.getImages());
        product.setAttributes(request.getAttributes());
        product.setActive(true); // Regla de negocio: Todo producto nuevo nace activo

        // 2. Guardar en Base de Datos
        Product savedProduct = productRepository.save(product);

        // 3. Mapeo Manual: Entidad -> DTO
        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getSlug(),
                savedProduct.getCategoryId(),
                savedProduct.getBasePrice(),
                savedProduct.getImages(),
                savedProduct.getAttributes(),
                savedProduct.isActive()
        );
    }
}