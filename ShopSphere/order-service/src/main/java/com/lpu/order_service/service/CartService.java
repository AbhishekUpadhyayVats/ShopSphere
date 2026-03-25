package com.lpu.order_service.service;


import com.lpu.order_service.dto.CartItemRequestDTO;

import com.lpu.order_service.dto.CartResponseDTO;

public interface CartService {

    CartResponseDTO createCart(Long userId,CartItemRequestDTO request);

    CartResponseDTO getCartByUserId(Long userId);

    void deleteCart(Long cartId);
}