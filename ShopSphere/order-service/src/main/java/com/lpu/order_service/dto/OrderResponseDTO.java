package com.lpu.order_service.dto;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.lpu.order_service.status.OrderStatus;

public class OrderResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long userId;
    private Double totalAmount;
    private String address;
    private String paymentMethod;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDTO> items;
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
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<OrderItemResponseDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemResponseDTO> items) {
		this.items = items;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Override
	public String toString() {
		return "OrderResponseDTO [id=" + id + ", userId=" + userId + ", totalAmount=" + totalAmount + ", address="
				+ address + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", items="
				+ items + "]";
	}
}