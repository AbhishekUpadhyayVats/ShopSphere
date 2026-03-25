package com.lpu.order_service.dto;

import java.io.Serializable;
import java.util.List;

public class CartResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long userId;
    private List<CartItemResponseDTO> items;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<CartItemResponseDTO> getItems() {
		return items;
	}
	public void setItems(List<CartItemResponseDTO> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "CartResponseDTO [id=" + id + ", userId=" + userId + ", items=" + items + "]";
	}
}