package com.lpu.order_service.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lpu.order_service.dto.*;
import com.lpu.order_service.entity.*;
import com.lpu.order_service.exception.CartNotFoundException;
import com.lpu.order_service.repository.CartRepository;
import com.lpu.order_service.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @CachePut(value = "cart", key = "#userId")
    public CartResponseDTO createCart(Long userId, CartItemRequestDTO request) {

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        //If cart doesn't exist → create new
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCartItem(new ArrayList<>());
        }

        //Check if product already exists in cart
        boolean itemExists = false;

        for (CartItem item : cart.getCartItem()) {
            if (item.getProductId().equals(request.getProductId())) {
                // update quantity
                item.setQuantity(item.getQuantity() + request.getQuantity());
                itemExists = true;
                break;
            }
        }

        //If not exists → add new item
        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            newItem.setCart(cart);

            cart.getCartItem().add(newItem);
        }

        Cart saved = cartRepository.save(cart);

        return mapToResponse(saved);
    }

    //Get cart
    @Override
    @Cacheable(value = "cart", key = "#userId")
    public CartResponseDTO getCartByUserId(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        
        //IMPORTANT CHECK
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return mapToResponse(cart);
    }

    //Delete cart
    @Override
    @CacheEvict(value = "cart", key = "#userId")
    public void deleteCart(Long cartId, Long userId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        
        //IMPORTANT CHECK
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        cartRepository.delete(cart);
    }

    //Mapper
    private CartResponseDTO mapToResponse(Cart cart) {

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());

        List<CartItemResponseDTO> items = cart.getCartItem().stream().map(item -> {
            CartItemResponseDTO i = new CartItemResponseDTO();
            i.setId(item.getId());
            i.setProductId(item.getProductId());
            i.setQuantity(item.getQuantity());
            return i;
        }).toList();

        dto.setItems(items);
        return dto;
    }
}