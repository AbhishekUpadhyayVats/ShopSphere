package com.lpu.email_service.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long userId;
    private Double amount;
    private String email;
    private String type;
    private LocalDateTime createdAt;

    public OrderEvent() {}

    public OrderEvent(Long id, Long userId, Double amount, String email, String type, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.email = email;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Double getAmount() { return amount; }
    public String getEmail() { return email; }
    public String getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setEmail(String email) { this.email = email; }
    public void setType(String type) { this.type = type; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}