package com.lpu.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lpu.payment_service.dto.PaymentRequestDTO;
import com.lpu.payment_service.dto.PaymentResponseDTO;
import com.lpu.payment_service.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Called by Order Service via Feign
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @RequestBody PaymentRequestDTO request) {

        PaymentResponseDTO response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
