package com.lpu.payment_service.service;

import com.lpu.payment_service.dto.PaymentRequestDTO;
import com.lpu.payment_service.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO request);
}
