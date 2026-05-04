package com.lpu.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lpu.payment_service.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
