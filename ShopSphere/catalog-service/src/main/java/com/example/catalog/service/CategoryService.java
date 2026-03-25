package com.example.catalog.service;

import java.util.List;

import com.example.catalog.dto.CategoryRequest;
import com.example.catalog.dto.CategoryResponse;

public interface CategoryService {

	CategoryResponse create(CategoryRequest request);

	CategoryResponse getById(Long id);

	List<CategoryResponse> getAll(int pageNumber, int pageSize);

	CategoryResponse update(Long id, CategoryRequest request);

	void delete(Long id);
}
