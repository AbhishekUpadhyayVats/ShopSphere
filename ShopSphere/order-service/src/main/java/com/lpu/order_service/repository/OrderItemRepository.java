package com.lpu.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lpu.order_service.entity.Order;
import com.lpu.order_service.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrder(Order order);
}
