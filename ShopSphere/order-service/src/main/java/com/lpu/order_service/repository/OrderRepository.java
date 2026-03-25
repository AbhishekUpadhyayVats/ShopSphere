package com.lpu.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lpu.order_service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    
    long count(); // total orders

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalRevenue();
}