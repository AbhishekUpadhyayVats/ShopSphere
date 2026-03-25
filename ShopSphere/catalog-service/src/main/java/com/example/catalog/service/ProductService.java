package com.example.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.catalog.dto.ProductRequest;
import com.example.catalog.dto.ProductResponse;

public interface ProductService {

	ProductResponse create(ProductRequest request);

    ProductResponse getById(Long id);

    Page<ProductResponse> getAll(String keyword, Pageable pageable);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
    
    ProductResponse reducingStock(Long id, int quantity);
    
    ProductResponse increasingStock(Long id, int quantity);
    
    Long getProductCount();
}
