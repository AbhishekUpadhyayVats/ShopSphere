package com.lpu.payment_service.serviceimpl;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.lpu.payment_service.dto.PaymentRequestDTO;
import com.lpu.payment_service.dto.PaymentResponseDTO;
import com.lpu.payment_service.entity.Payment;
import com.lpu.payment_service.feignclient.OrderClient;
import com.lpu.payment_service.repository.PaymentRepository;
import com.lpu.payment_service.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final RazorpayClient razorpayClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderClient orderClient,
                              RazorpayClient razorpayClient) {
        this.paymentRepository = paymentRepository;
        this.orderClient = orderClient;
        this.razorpayClient = razorpayClient;
    }

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setCreatedAt(LocalDateTime.now());

        PaymentResponseDTO response = new PaymentResponseDTO();

        try {

            // ✅ HANDLE COD (NO RAZORPAY)
            if ("COD".equalsIgnoreCase(request.getPaymentMethod())) {

                payment.setStatus("SUCCESS");
                payment.setUpdatedAt(LocalDateTime.now());

                Payment saved = paymentRepository.save(payment);

                // Update order status to CONFIRMED for COD
                orderClient.updateOrderStatusInternal(request.getOrderId(), "PACKED");

                response.setPaymentId(saved.getId());
                response.setOrderId(saved.getOrderId());
                response.setAmount(saved.getAmount());
                response.setStatus("SUCCESS");
                response.setMessage("Order placed successfully with Cash on Delivery");

                return response;
            }

            // ✅ ONLINE PAYMENT — create Razorpay order (amount in paise)
            int amountInPaise = (int) Math.round(request.getAmount() * 100);

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt", "order_" + request.getOrderId());

            // Create Razorpay order
            Order razorpayOrder = razorpayClient.orders.create(options);
            String razorpayOrderId = razorpayOrder.get("id");

            payment.setStatus("PAID");
            payment.setUpdatedAt(LocalDateTime.now());
            payment.setRazorpayOrderId(razorpayOrderId);

            Payment saved = paymentRepository.save(payment);

            response.setPaymentId(saved.getId());
            response.setOrderId(saved.getOrderId());
            response.setAmount(saved.getAmount());
            response.setStatus("PAID");
            response.setMessage("Razorpay order created");
            response.setRazorpayOrderId(razorpayOrderId);

        } catch (Exception e) {

            payment.setStatus("FAILED");
            payment.setFailureReason(e.getMessage() != null ? e.getMessage() : "Unknown error");
            payment.setUpdatedAt(LocalDateTime.now());

            paymentRepository.save(payment);

            // Try updating order status to FAILED
            try {
                orderClient.updateOrderStatusInternal(request.getOrderId(), "FAILED");
            } catch (Exception ex) {
                System.out.println("Order status update failed: " + ex.getMessage());
            }

            response.setStatus("FAILED");
            response.setMessage("Payment creation failed");
        }

        return response;
    }
}
