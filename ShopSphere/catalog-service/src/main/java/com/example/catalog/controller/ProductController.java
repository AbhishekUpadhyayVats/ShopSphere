package com.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.catalog.dto.ProductRequest;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/gettingProduct/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        ProductResponse response = productService.getById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(

            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProductResponse> products = productService.getAll(keyword, pageable);

        return ResponseEntity.ok(products);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deletingProduct/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    
    @PutMapping("/reduceStock/{id}")
    public ResponseEntity<String> reducingStock(@PathVariable Long id, @RequestParam int quantity){
    	productService.reducingStock(id, quantity);
    	
    	return ResponseEntity.ok("Stock decreased of product " + id);
    }
    
    @PutMapping("/increaseStock/{id}")
    public ResponseEntity<String> increasingStock(@PathVariable Long id, @RequestParam int quantity){
    	productService.increasingStock(id, quantity);
    	
    	return ResponseEntity.ok("Stock increased of product " + id);
    }

    // TOTAL PRODUCTS
    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        return ResponseEntity.ok(productService.getProductCount());
    }
}