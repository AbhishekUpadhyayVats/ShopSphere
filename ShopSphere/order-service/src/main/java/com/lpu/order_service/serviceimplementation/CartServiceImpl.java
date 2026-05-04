package com.lpu.order_service.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

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

    // Add item to cart (or increase quantity if already exists)
    @Override
    public CartResponseDTO createCart(Long userId, CartItemRequestDTO request) {

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCartItem(new ArrayList<>());
        }

        boolean itemExists = false;
        for (CartItem item : cart.getCartItem()) {
            if (item.getProductId().equals(request.getProductId())) {
                item.setQuantity(item.getQuantity() + request.getQuantity());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            newItem.setCart(cart);
            cart.getCartItem().add(newItem);
        }

        return mapToResponse(cartRepository.save(cart));
    }

    // Get cart — returns empty cart DTO if none exists (no exception)
    @Override
    public CartResponseDTO getCartByUserId(Long userId) {

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            CartResponseDTO empty = new CartResponseDTO();
            empty.setUserId(userId);
            empty.setItems(new ArrayList<>());
            return empty;
        }

        return mapToResponse(cart);
    }

    // Delete entire cart
    @Override
    public void deleteCart(Long cartId, Long userId) {

        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            return; // already gone — treat as success
        }

        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        cartRepository.delete(cart);
    }

    // Update quantity of a specific cart item
    @Override
    public CartResponseDTO updateCartItemQuantity(Long userId, Long itemId, int quantity) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem target = cart.getCartItem().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + itemId));

        if (quantity <= 0) {
            cart.getCartItem().remove(target);
        } else {
            target.setQuantity(quantity);
        }

        return mapToResponse(cartRepository.save(cart));
    }

    // Remove a single item from cart
    @Override
    public CartResponseDTO removeCartItem(Long userId, Long itemId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        boolean removed = cart.getCartItem().removeIf(i -> i.getId().equals(itemId));

        if (!removed) {
            throw new RuntimeException("Cart item not found: " + itemId);
        }

        return mapToResponse(cartRepository.save(cart));
    }

    // Mapper
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
