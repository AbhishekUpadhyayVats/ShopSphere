package com.lpu.order_service.dto;

import java.io.Serializable;

public class OrderItemResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long productId;
    private int quantity;
    private Double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "OrderItemResponseDTO [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", price="
				+ price + "]";
	}
}
