package com.example.catalog.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.catalog.entity.Product;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String to,Product product) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(to);
		
		message.setSubject("New Product Added: " + product.getName());
		
		message.setText(
				 "A new product has been added:\n\n" +
			      "Product Name: " + product.getName() + "\n" +
			      "Description: " + product.getDescription() + "\n" +
			      "Price: " + product.getPrice() + "\n" +
			      "Category: " + product.getCategory().getName()
		);
		
		mailSender.send(message);
	}
}
