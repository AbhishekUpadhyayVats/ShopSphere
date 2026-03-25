package com.lpu.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {
	
	@NotNull
	private AddressRequestDTO addressRequestDTO;
	
	@NotBlank
	private String paymentMethod;
	
	public AddressRequestDTO getAddressRequestDTO() {
		return addressRequestDTO;
	}
	public void setAddressRequestDTO(AddressRequestDTO addressRequestDTO) {
		this.addressRequestDTO = addressRequestDTO;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
