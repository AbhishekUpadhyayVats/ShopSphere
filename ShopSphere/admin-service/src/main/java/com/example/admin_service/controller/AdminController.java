package com.example.admin_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.admin_service.dto.DashboardResponseDTO;
import com.example.admin_service.dto.OrderResponseDTO;
import com.example.admin_service.dto.ReportResponseDTO;
import com.example.admin_service.service.AdminService;
import com.example.admin_service.status.OrderStatus;

@RestController
//@PreAuthorize("hasRole('ADMIN')") //secure all endpoints -> we commented because already authenticated in SecurityConfig
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //DASHBOARD
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    //GET ALL ORDERS
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    //UPDATE ORDER STATUS
    @PutMapping("/order/status/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(adminService.updateOrderStatus(orderId, status));
    }

    //REPORTS
    @GetMapping("/reports")
    public ResponseEntity<ReportResponseDTO> getReports() {
        return ResponseEntity.ok(adminService.getReports());
    }
}