package com.lpu.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lpu.order_service.dto.CartItemRequestDTO;
import com.lpu.order_service.dto.CartResponseDTO;
import com.lpu.order_service.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //Add item to cart
    @PostMapping
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CartItemRequestDTO request) {

        CartResponseDTO response = cartService.createCart(userId, request);
        return ResponseEntity.ok(response);
    }

    //Get cart
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    //Delete cart (clear all items)
    @DeleteMapping
    public ResponseEntity<String> deleteCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.deleteCart(null, userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    //Update cart item quantity
    @PutMapping("/item/{itemId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @PathVariable Long itemId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam int quantity) {

        CartResponseDTO response = cartService.updateCartItemQuantity(userId, itemId, quantity);
        return ResponseEntity.ok(response);
    }

    //Remove single item from cart
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<CartResponseDTO> removeCartItem(
            @PathVariable Long itemId,
            @RequestHeader("X-User-Id") Long userId) {

        CartResponseDTO response = cartService.removeCartItem(userId, itemId);
        return ResponseEntity.ok(response);
    }
}