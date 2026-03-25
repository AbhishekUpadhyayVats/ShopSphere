package com.example.catalog.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.catalog.dto.ProductRequest;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.emailservice.EmailService;
import com.example.catalog.entity.Category;
import com.example.catalog.entity.Product;
import com.example.catalog.exception.CategoryNotFoundException;
import com.example.catalog.exception.ProductNotFoundException;
import com.example.catalog.repository.CategoryRepository;
import com.example.catalog.repository.ProductRepository;
import com.example.catalog.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	@CachePut(value = "products", key = "#result.id")
	public ProductResponse create(ProductRequest request) {
		Product product = productReqToProduct(request);
		
		Product product1 = productRepo.save(product);
		
		emailService.sendMail("abhi2002upadhyay@gmail.com", product1);
		
		return productToProductResponse(product1);
	}

	@Override
	@Cacheable(value = "products", key = "#id")
	public ProductResponse getById(Long id) {
		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with ID: " + id + " does not exists"));
		return productToProductResponse(product);
	}

	@Override
	@Cacheable(
		    value = "productsList",
		    key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
		)
	public Page<ProductResponse> getAll(String keyword, Pageable pageable) {
		Page<Product> page = productRepo.findByNameContainingIgnoreCase(keyword, pageable);
		
		return page.map(n -> productToProductResponse(n));
	}

	@Override
	@CachePut(value = "products", key = "#id")
	@CacheEvict(value = "productsList", allEntries = true)
	public ProductResponse update(Long id, ProductRequest request) {
		Product product = productRepo.findById(id)
	            .orElseThrow(() -> new ProductNotFoundException(
	                    "Product with ID: " + id + " does not exist"));

	    Category category = categoryRepo.findById(request.getCategoryId())
	            .orElseThrow(() -> new CategoryNotFoundException(
	                    "Category with ID: " + request.getCategoryId() + " does not exist"));

	    product.setName(request.getName());
	    product.setDescription(request.getDescription());
	    product.setPrice(request.getPrice());
	    product.setStock(request.getStock());
	    product.setCategory(category);

	    Product savedProduct = productRepo.save(product);

	    return productToProductResponse(savedProduct);
	}

	@Override
	/*@CacheEvict(value = {"products", "productsList"}, allEntries = true)
	 *This means:
	 *-> Clear ALL entries from products cache
	 *-> Clear ALL entries from productsList cache
	 */
	
	@Caching(evict = {
		    @CacheEvict(value = "products", key = "#id"),
		    @CacheEvict(value = "productsList", allEntries = true)
	})
	public void delete(Long id) {
		productRepo.deleteById(id);
	}
	
	@Override
	public ProductResponse reducingStock(Long id, int quantity) {
		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product found with ID: " + id));
		int stock = product.getStock();
		if(quantity>stock) {
			throw new RuntimeException("Quantity is more than Stock");
		}
		stock = stock - quantity;
		product.setStock(stock);
		
		productRepo.save(product);
		
		return productToProductResponse(product);
	}
	
	@Override
	public ProductResponse increasingStock(Long id, int quantity) {
		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product found with ID: " + id));
		int stock = product.getStock();

		stock = stock + quantity;
		product.setStock(stock);
		
		productRepo.save(product);
		
		return productToProductResponse(product);
	}
	
	public Long getProductCount() {
	    return productRepo.count();
	}

	public Product productReqToProduct(ProductRequest productRequest) {
		Long id = productRequest.getCategoryId();
		Category category = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID:" + id + " does not exists"));
		Product product = new Product();
		product.setName(productRequest.getName());
		product.setDescription(productRequest.getDescription());
		product.setCategory(category);
		product.setStock(productRequest.getStock());
		product.setPrice(productRequest.getPrice());
		
		return product;
	}
	
	
	public ProductResponse productToProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setCategoryName(product.getCategory().getName());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		
		return productResponse;
	}

}
