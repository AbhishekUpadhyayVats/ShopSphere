package com.example.admin_service.dto;

import java.io.Serializable;

public class ReportResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Double totalRevenue;
    private Long totalOrders;
    
	public Double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public Long getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}
}