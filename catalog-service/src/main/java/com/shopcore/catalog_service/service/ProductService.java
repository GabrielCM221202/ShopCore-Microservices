package com.shopcore.catalog_service.service;

import com.shopcore.catalog_service.dto.PageResponse;
import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    PageResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir);
    ProductResponse getProductById(String id);
    ProductResponse updateProduct(String id, ProductRequest request);
    void deleteProduct(String id);
}
