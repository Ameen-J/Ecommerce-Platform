package com.ecommerce.project.controller;

import com.ecommerce.project.model.Order;
import com.ecommerce.project.model.OrderItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repo.OrderRepository;
import com.ecommerce.project.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderController(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        double totalOrderPrice = 0.0;

        if (order.getItems() == null || order.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Order must contain at least one product");
        }

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElse(null);

            if (product == null) {
                return ResponseEntity.badRequest()
                        .body("Product not found: " + item.getProduct().getId());
            }

            if (item.getQuantity() > product.getStock()) {
                return ResponseEntity.badRequest()
                        .body("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepo.save(product);

            item.setTotalPrice(product.getPrice() * item.getQuantity());
            item.setOrder(order);

            totalOrderPrice += item.getTotalPrice();
        }

        order.setTotalPrice(totalOrderPrice);
        orderRepo.save(order);

        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderRepo.findByUserId(userId));
    }
}
