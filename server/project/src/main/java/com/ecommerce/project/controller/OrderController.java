package com.ecommerce.project.controller;

import com.ecommerce.project.model.Order;
import com.ecommerce.project.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.UserRepository;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/my-orders")
    public List<Order> getMyOrders(Authentication authentication) {
        String email = authentication.getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderService.getUserOrders(user.getId());
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/place")
    public Order placeOrder(@RequestBody Map<String, Object> request, Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        return orderService.placeOrder(user.getId(), items);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
