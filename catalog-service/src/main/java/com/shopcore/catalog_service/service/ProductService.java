package com.shopcore.catalog_service.service;

import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
}
