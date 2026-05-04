package com.lpu.order_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.lpu.order_service.dto.PaymentRequestDTO;
import com.lpu.order_service.dto.PaymentResponseDTO;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payment/process")
    PaymentResponseDTO processPayment(
            @RequestBody PaymentRequestDTO request
    );
}