package com.lpu.order_service.dto;

import java.io.Serializable;

public class ProductResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Long id;
    private String name;
    private double price;
    private String categoryName;
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
	public ProductResponse() {
		super();
	}
	@Override
	public String toString() {
		return "ProductResponse [id=" + id + ", name=" + name + ", price=" + price + ", categoryName=" + categoryName
				+ "]";
	}
}
