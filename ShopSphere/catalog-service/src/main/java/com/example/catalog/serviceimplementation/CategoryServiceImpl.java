package com.example.catalog.serviceimplementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.catalog.dto.CategoryRequest;
import com.example.catalog.dto.CategoryResponse;
import com.example.catalog.entity.Category;
import com.example.catalog.repository.CategoryRepository;
import com.example.catalog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;

	
    @Override
    @CachePut(value = "categories", key = "#result.id")
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryReqToCategory(request);
        Category saved = categoryRepo.save(category);
        return categoryToCategoryResponse(saved);
    }

    
    @Override
    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse getById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        return categoryToCategoryResponse(category);
    }


    @Override
	@Cacheable(
		    value = "categoriesList",
		    key = "#pageNumber + '_' + #pageSize"
		)
    public List<CategoryResponse> getAll(int pageNumber, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNumber, pageSize);
    	Page<Category> page = categoryRepo.findAll(pageable);

        return page.getContent()
                .stream()
                .map(this::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }


    @Override
    @CachePut(value = "categories", key = "#id")
    @CacheEvict(value = "categoriesList", allEntries = true)
    public CategoryResponse update(Long id, CategoryRequest request) {

        Category existing = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        Category updated = categoryRepo.save(existing);

        return categoryToCategoryResponse(updated);
    }

    @Override
	@Caching(evict = {
		    @CacheEvict(value = "categories", key = "#id"),
		    @CacheEvict(value = "categoriesList", allEntries = true)
	})    public void delete(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryRepo.delete(category);
    }


    public Category categoryReqToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }


    public CategoryResponse categoryToCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
