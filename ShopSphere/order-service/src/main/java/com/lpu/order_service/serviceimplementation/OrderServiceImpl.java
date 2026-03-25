package com.lpu.order_service.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lpu.order_service.dto.*;
import com.lpu.order_service.emailservice.EmailService;
import com.lpu.order_service.entity.*;
import com.lpu.order_service.exception.OrderNotFoundException;
import com.lpu.order_service.repository.AddressRepository;
import com.lpu.order_service.repository.CartRepository;
import com.lpu.order_service.repository.OrderRepository;
import com.lpu.order_service.feignclient.ProductClient;
import com.lpu.order_service.service.OrderService;
import com.lpu.order_service.status.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductClient productClient;
    private final EmailService emailService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            ProductClient productClient,
                            EmailService emailService, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productClient = productClient;
        this.emailService = emailService;
        this.addressRepository = addressRepository;
    }

    //PLACE ORDER FROM CART (SAME SERVICE)
    @Override
    @CachePut(value = "order", key = "#result.id")
    @CacheEvict(value = "cart", key = "#userId")
    public OrderResponseDTO placeOrder(Long userId, OrderRequestDTO request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItem() == null || cart.getCartItem().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId);
        
        AddressRequestDTO address = request.getAddressRequestDTO();
        
        Address newAddress = new Address();
        newAddress.setUserId(userId);
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        newAddress.setPincode(address.getPincode());

        addressRepository.save(newAddress);
        
        //SET ADDRESS
        order.setAddress(
            newAddress.getStreet() + ", " +
            newAddress.getCity() + ", " +
            newAddress.getState() + " - " +
            newAddress.getPincode()
        );
        
        order.setPaymentMethod(request.getPaymentMethod());

        order.setStatus(OrderStatus.DRAFT);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        List<OrderItem> items = cart.getCartItem().stream().map(i -> {

            ProductResponse product = productClient.getProduct(i.getProductId());

            OrderItem item = new OrderItem();
            item.setProductId(i.getProductId());
            item.setQuantity(i.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);

            productClient.reduceStock(i.getProductId(), i.getQuantity());

            return item;

        }).toList();

        order.setItems(items);

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        //clear cart
        cart.getCartItem().clear();
        cartRepository.save(cart);

        emailService.sendMail("abhi2002upadhyay@gmail.com", saved);

        return mapToResponse(saved);
    }
    
    
    //GET ORDER
    @Cacheable(value = "order", key = "#orderId + '-' + #userId")
    public OrderResponseDTO getOrderById(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        //IMPORTANT CHECK
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order");
        }
        return mapToResponse(order);
    }


    //DELETE ORDER
    @Override
    @CacheEvict(value = "order", key = "#orderId + '-' + #userId")
    public void deleteOrder(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        //IMPORTANT CHECK
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order");
        }

        for(OrderItem orderItem : order.getItems()) {
        	Long productId = orderItem.getProductId();
        	int quantity = orderItem.getQuantity();
        	productClient.increasingStock(productId,quantity);
        }
        
        orderRepository.delete(order);
    }
     
    //UPDATE ONLY PAYMENT METHOD
    @Override
    @CachePut(value = "order", key = "#orderId + '-' + #userId")
    public OrderResponseDTO updateOrder(Long orderId, Long userId, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        //IMPORTANT CHECK
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order");
        }

        order.setPaymentMethod(paymentMethod);
        order.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(orderRepository.save(order));
    }
    
    
    //UPDATE ONLY STATUS
    //ADMIN ONLY
    @Override
    @CachePut(value = "order", key = "#orderId")
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status) {
    	
    	Order order = orderRepository.findById(orderId)
    			.orElseThrow(() -> new OrderNotFoundException("Order not found"));
    	
    	order.setStatus(status);
    	order.setUpdatedAt(LocalDateTime.now());
    	
    	return mapToResponse(orderRepository.save(order));
    }
    
    //ADMIN ONLY
    @Override
    public Long getTotalOrders() {
        return orderRepository.count();
    }

    //ADMIN ONLY
    @Override
    public Double getTotalRevenue() {
        Double revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    //ADMIN ONLY
    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

    //MAPPER
    private OrderResponseDTO mapToResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setAddress(order.getAddress());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setPaymentMethod(order.getPaymentMethod());

        dto.setItems(order.getItems().stream().map(i -> {
            OrderItemResponseDTO item = new OrderItemResponseDTO();
            item.setId(i.getId());
            item.setProductId(i.getProductId());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            return item;
        }).toList());

        return dto;
    }
}