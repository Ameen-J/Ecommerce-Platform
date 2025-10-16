package com.ecommerce.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
