package com.shopcore.catalog_service.service.impl;

import com.shopcore.catalog_service.dto.PageResponse;
import com.shopcore.catalog_service.dto.ProductRequest;
import com.shopcore.catalog_service.dto.ProductResponse;
import com.shopcore.catalog_service.exception.ResourceNotFoundException;
import com.shopcore.catalog_service.mapper.ProductMapper;
import com.shopcore.catalog_service.model.Product;
import com.shopcore.catalog_service.model.ProductStatus;
import com.shopcore.catalog_service.repository.ProductRepository;
import com.shopcore.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {

        // 1. Configuramos la dirección del ordenamiento (Ascendente o Descendente)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // 2. Creamos el objeto Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Consultamos a MongoDB (el MongoRepository ya trae findAll(Pageable) por defecto)
        Page<Product> productPage = productRepository.findByStatus(ProductStatus.ACTIVE, pageable);

        // 4. Mapeamos el contenido de Entidad a DTO
        List<ProductResponse> content = productPage.getContent()
                .stream()
                .map(productMapper::toResponse)
                .toList();

        // 5. Construimos y retornamos nuestro DTO personalizado
        return new PageResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto No Encontrado con el ID: " + id)
        );
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));

        // Actualizamos los campos (puedes usar tu mapper, pero a mano se ve así)
        existingProduct.setName(request.getName());
        existingProduct.setSlug(request.getSlug());
        existingProduct.setCategoryId(request.getCategoryId());
        existingProduct.setBasePrice(request.getBasePrice());
        existingProduct.setImages(request.getImages());
        existingProduct.setAttributes(request.getAttributes());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));

        // Borrado lógico
        existingProduct.setStatus(ProductStatus.INACTIVE);
        productRepository.save(existingProduct);
    }


}