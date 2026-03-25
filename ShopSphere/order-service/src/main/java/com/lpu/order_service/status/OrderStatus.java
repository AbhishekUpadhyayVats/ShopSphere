package com.lpu.order_service.status;

public enum OrderStatus {
    DRAFT,
    CHECKOUT,
    PAID,
    PACKED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    FAILED
}