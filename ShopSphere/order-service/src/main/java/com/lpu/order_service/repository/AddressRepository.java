package com.lpu.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lpu.order_service.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	Address findByIdAndUserId(Long id, Long userId);
}
