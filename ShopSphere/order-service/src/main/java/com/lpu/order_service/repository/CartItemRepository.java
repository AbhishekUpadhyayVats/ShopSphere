package com.lpu.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lpu.order_service.entity.Cart;
import com.lpu.order_service.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
}