//package com.lpu.order_service.emailservice;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import com.lpu.order_service.entity.Order;
//
//
//@Service
//public class EmailService {
//
//	@Autowired
//	private JavaMailSender mailSender;
//	
//	public void sendMail(String to,Order order) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(to);
//
//        message.setSubject("Order Confirmation - Order ID: " + order.getId());
//
//        message.setText(
//                "Your order has been placed successfully!\n\n" +
//                "Order ID: " + order.getId() + "\n" +
//                "Address: " + order.getAddress() + "\n" +
//                "Total Amount: ₹" + order.getTotalAmount() + "\n" +
//                "Status: " + order.getStatus() + "\n\n" +
//                "Thank you for shopping with us!"
//        );
//
//        mailSender.send(message);
//	}
//}
