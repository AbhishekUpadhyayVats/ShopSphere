package com.lpu.email_service.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.lpu.email_service.dto.OrderEvent;

@Service
public class EmailListener {

    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email.queue")
    public void consume(OrderEvent event) {

        if ("ORDER_CREATED".equals(event.getType())) {

            emailService.send(
                    event.getEmail(),
                    "Order Confirmation",
                    "Order ID: " + event.getId() +
                    "\nAmount: ₹" + event.getAmount()
            );

        } else if ("PRODUCT_CREATED".equals(event.getType())) {

            emailService.send(
                    event.getEmail(),
                    "Product Created",
                    "Product ID: " + event.getId() + ", Product Price: " + event.getAmount()
            );
        }
    }
}