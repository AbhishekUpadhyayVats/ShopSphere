package com.example.catalog.dto;

import java.io.Serializable;

public class ProductResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Long id;
    private String name;
    private double price;
    private String categoryName;
    private String description;
    private int stock;
    private String imageUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public ProductResponse() {
		super();
	}
	@Override
	public String toString() {
		return "ProductResponse [id=" + id + ", name=" + name + ", price=" + price + ", categoryName=" + categoryName
				+ "]";
	}
}
