package com.ecommerce.project.service;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.Order;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
            CartItemRepository cartItemRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderService = orderService;
    }

    public Cart getUserCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setTotalPrice(0);
                    return cartRepository.save(newCart);
                });
    }

    public Cart addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getUserCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getQuantity() * product.getPrice());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);

            newItem.setTotalPrice(product.getPrice() * quantity);
            cart.getItems().add(newItem);
        }

        cart.setTotalPrice(cart.getItems().stream().mapToDouble(CartItem::getTotalPrice).sum());
        return cartRepository.save(cart);
    }

    public Cart updateCartItemQuantity(Long userId, Long cartItemId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Cart cart = getUserCart(userId);

        CartItem itemToUpdate = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        itemToUpdate.setQuantity(newQuantity);
        itemToUpdate.setTotalPrice(newQuantity * itemToUpdate.getProduct().getPrice());

        cart.setTotalPrice(cart.getItems().stream().mapToDouble(CartItem::getTotalPrice).sum());

        return cartRepository.save(cart);
    }

    public Order checkout(Long userId) {
        Cart cart = getUserCart(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot checkout");
        }

        List<Map<String, Object>> items = cart.getItems().stream()
                .map(cartItem -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("productId", cartItem.getProduct().getId());
                    map.put("quantity", cartItem.getQuantity());
                    return map;
                })
                .collect(Collectors.toList());

        Order order = orderService.placeOrder(userId, items);

        clearCart(userId);

        return order;
    }

    public void clearCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.getItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    public Cart deleteCartItem(Long userId, Long cartItemId) {
        Cart cart = getUserCart(userId);

        CartItem itemToRemove = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.getItems().remove(itemToRemove);
        cart.setTotalPrice(cart.getItems().stream().mapToDouble(CartItem::getTotalPrice).sum());

        cartItemRepository.delete(itemToRemove);

        return cartRepository.save(cart);
    }
}
