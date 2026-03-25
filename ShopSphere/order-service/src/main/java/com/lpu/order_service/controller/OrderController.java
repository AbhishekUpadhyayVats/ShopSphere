package com.lpu.order_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lpu.order_service.dto.OrderRequestDTO;
import com.lpu.order_service.dto.OrderResponseDTO;
import com.lpu.order_service.service.OrderService;
import com.lpu.order_service.status.OrderStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //Place order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody OrderRequestDTO request) {

        return ResponseEntity.ok(orderService.placeOrder(userId, request));
    }

    //Get order
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId, @RequestHeader("X-User-Id") Long userId) {
//    	System.err.println(userId);
        return ResponseEntity.ok(orderService.getOrderById(orderId,userId));
    }

    //Update payment method
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam String paymentMethod) {

        return ResponseEntity.ok(orderService.updateOrder(orderId, userId, paymentMethod));
    }
    
    //Delete order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
    		@PathVariable Long orderId, @RequestHeader("X-User-Id") Long userId) {
    	
    	orderService.deleteOrder(orderId,userId);
    	return ResponseEntity.ok("Order deleted successfully");
    }
    
    //Update only status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/status/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam  OrderStatus status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
    
    
    // ================= ADMIN APIs =================

    //GET ALL ORDERS (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    //TOTAL ORDERS COUNT (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total-orders")
    public ResponseEntity<Long> getTotalOrders() {
        return ResponseEntity.ok(orderService.getTotalOrders());
    }

    //TOTAL REVENUE (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(orderService.getTotalRevenue());
    }
}