package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.service.CartService;

import com.ecommerce.project.model.User;
import com.ecommerce.project.model.Order;
import com.ecommerce.project.repository.UserRepository;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Cart getUserCart(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartService.getUserCart(user.getId());
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody Map<String, Object> request,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());

        return cartService.addToCart(user.getId(), productId, quantity);
    }

    @PostMapping("/checkout")
    public Order checkout(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartService.checkout(user.getId());

    }

    @DeleteMapping("/remove/{productId}")
    public Cart removeCartItem(@PathVariable Long productId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartService.deleteCartItem(user.getId(), productId);
    }

    @DeleteMapping("/clear")
    public Cart clearCart(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartService.clearCart(user.getId());
        return cartService.getUserCart(user.getId());
    }
}
