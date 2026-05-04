package com.lpu.payment_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    // Internal endpoint — no @PreAuthorize, safe for service-to-service calls
    @PutMapping("/order/internal/status/{orderId}")
    void updateOrderStatusInternal(
            @PathVariable Long orderId,
            @RequestParam String status);
}
