package com.lpu.order_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lpu.order_service.config.FeignConfig;
import com.lpu.order_service.dto.ProductResponse;

@FeignClient(name = "CATALOG-SERVICE", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/product/{id}")
    ProductResponse getProduct(@PathVariable Long id);

    @PutMapping("/product/reduceStock/{id}")
    void reduceStock(@PathVariable Long id, @RequestParam int quantity);
    
    @PutMapping("/product/increaseStock/{id}")
    void increasingStock(@PathVariable Long id, @RequestParam int quantity);
}
