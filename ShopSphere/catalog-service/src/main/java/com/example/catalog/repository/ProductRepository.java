package com.example.catalog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.catalog.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	long count();
}
