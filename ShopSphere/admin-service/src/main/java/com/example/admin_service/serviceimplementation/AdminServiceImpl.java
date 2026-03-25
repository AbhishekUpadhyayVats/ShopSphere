package com.example.admin_service.serviceimplementation;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.example.admin_service.dto.DashboardResponseDTO;
import com.example.admin_service.dto.OrderResponseDTO;
import com.example.admin_service.dto.ReportResponseDTO;
import com.example.admin_service.feign.CatalogClient;
import com.example.admin_service.feign.OrderClient;
import com.example.admin_service.service.AdminService;
import com.example.admin_service.status.OrderStatus;

@Service
public class AdminServiceImpl implements AdminService {

    private final OrderClient orderClient;
    private final CatalogClient catalogClient;

    public AdminServiceImpl(OrderClient orderClient,
                            CatalogClient catalogClient) {
        this.orderClient = orderClient;
        this.catalogClient = catalogClient;
    }

    //DASHBOARD (Cache)
    @Override
    @Cacheable(value = "adminDashboard")
    public DashboardResponseDTO getDashboard() {

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setTotalOrders(orderClient.getTotalOrders().getBody());
        dto.setTotalRevenue(orderClient.getTotalRevenue().getBody());
        dto.setTotalProducts(catalogClient.getProductCount());

        return dto;
    }

    //GET ALL ORDERS (Cache)
    @Override
    @Cacheable(value = "adminOrders")
    public List<OrderResponseDTO> getAllOrders() {
        return orderClient.getAllOrders().getBody();
    }

    //UPDATE ORDER STATUS (Update cache)
    @Override
//    @CachePut(value = "adminOrders")  -> dont do it will make cache inconsistent because it will take key as what are in method paramaters -> in that method orderId + status becomes key
    @CacheEvict(value = "adminOrders", allEntries = true)
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status) {
        return orderClient.updateOrderStatus(orderId, status).getBody();
    }

    //REPORTS (Cache)
    @Override
    @Cacheable(value = "adminReports")
    public ReportResponseDTO getReports() {

        ReportResponseDTO dto = new ReportResponseDTO();

        dto.setTotalOrders(orderClient.getTotalOrders().getBody());
        dto.setTotalRevenue(orderClient.getTotalRevenue().getBody());

        return dto;
    }
}