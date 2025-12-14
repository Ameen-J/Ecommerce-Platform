package com.ecommerce.project.service;

import com.ecommerce.project.model.Order;
import com.ecommerce.project.model.OrderItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.OrderRepository;
import com.ecommerce.project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order placeOrder(Long userId, List<Map<String, Object>> items) {
        Order order = new Order();
        order.setUserId(userId);

        double total = 0;

        for (Map<String, Object> itemData : items) {
            Long productId = Long.valueOf(itemData.get("productId").toString());
            int quantity = Integer.parseInt(itemData.get("quantity").toString());

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double itemTotal = product.getPrice() * quantity;
            total += itemTotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(itemTotal);
            orderItem.setOrder(order);

            order.getItems().add(orderItem);
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUserId().equals(userId))
                .toList();
    }
}
