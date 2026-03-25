package com.example.admin_service.service;

import java.util.List;

import com.example.admin_service.dto.DashboardResponseDTO;
import com.example.admin_service.dto.OrderResponseDTO;
import com.example.admin_service.dto.ReportResponseDTO;
import com.example.admin_service.status.OrderStatus;

public interface AdminService {

    DashboardResponseDTO getDashboard();

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status);

    ReportResponseDTO getReports();
}