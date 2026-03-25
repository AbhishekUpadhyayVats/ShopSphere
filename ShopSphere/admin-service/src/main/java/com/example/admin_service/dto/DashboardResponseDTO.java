package com.example.admin_service.dto;

import java.io.Serializable;

public class DashboardResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long totalOrders;
    private Double totalRevenue;
    private Long totalProducts;
	public Long getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}
	public Double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public Long getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(Long totalProducts) {
		this.totalProducts = totalProducts;
	}
}