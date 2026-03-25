package com.example.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequest {
	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String name;

	@NotBlank(message = "Description is required")
	@Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
	private String description;

	@Positive(message = "Price must be greater than 0")
	private double price;

	@Min(value = 0, message = "Stock cannot be negative")
	private int stock;

	@NotNull
	private Long categoryId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ProductRequest [name=" + name + ", description=" + description + ", price=" + price + ", stock=" + stock
				+ ", categoryId=" + categoryId + "]";
	}

	public ProductRequest() {
		super();
	}
}
