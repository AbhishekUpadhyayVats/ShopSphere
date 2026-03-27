package com.example.auth_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//Logger
		logger.info("New User Signed Up, Name: " + user.getName());
		
		return userRepo.save(user);
	}
	
	public User findByUsername(String email) {
		return userRepo.findByEmail(email)
			       .orElseThrow(() -> new RuntimeException("User not found"));
	}
}
