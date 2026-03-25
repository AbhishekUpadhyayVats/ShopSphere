package com.lpu.order_service.dto;

import java.io.Serializable;

public class CartItemResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long productId;
    private int quantity;
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
	@Override
	public String toString() {
		return "CartItemResponseDTO [id=" + id + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
}