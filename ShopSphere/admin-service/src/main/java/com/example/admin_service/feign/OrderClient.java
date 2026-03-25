package com.example.admin_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.admin_service.config.FeignConfig;
import com.example.admin_service.dto.OrderResponseDTO;
import com.example.admin_service.status.OrderStatus;


@FeignClient(name = "ORDER-SERVICE", configuration = FeignConfig.class)
public interface OrderClient {

	//get all Order
    @GetMapping("/order/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders();

    //Get order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId, @RequestHeader("X-User-Id") Long userId);

    @PutMapping("/order/status/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam  OrderStatus status) ;

    @GetMapping("/order/total-orders")
    public ResponseEntity<Long> getTotalOrders();

    @GetMapping("/order/total-revenue")
    public ResponseEntity<Double> getTotalRevenue(); 
}