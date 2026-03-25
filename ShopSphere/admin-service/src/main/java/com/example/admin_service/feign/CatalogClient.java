package com.example.admin_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.admin_service.config.FeignConfig;

@FeignClient(name = "CATALOG-SERVICE", configuration = FeignConfig.class)
public interface CatalogClient {

    @GetMapping("/product/count")
    public Long getProductCount(); 
}