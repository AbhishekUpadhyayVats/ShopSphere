package com.lpu.order_service.service;

import java.util.List;

import com.lpu.order_service.dto.OrderRequestDTO;
import com.lpu.order_service.dto.OrderResponseDTO;
import com.lpu.order_service.status.OrderStatus;

public interface OrderService {

    OrderResponseDTO placeOrder(Long userId, OrderRequestDTO request);

    OrderResponseDTO getOrderById(Long orderId, Long userId);

    OrderResponseDTO updateOrder(Long orderId, Long userId, String paymentMethod);

    void deleteOrder(Long orderId, Long userId);
    
    //ADMIN
    Long getTotalOrders();

    //ADMIN
    Double getTotalRevenue();

    //ADMIN
    List<OrderResponseDTO> getAllOrders();
    
    //ADMIN
    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status);
}