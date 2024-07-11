package com.closing.inventory.repository.product;

import com.closing.inventory.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByNameAndSizeAndWidth(String name, String size, String width);

    Product findByNameAndSizeAndWidth(String name, String size, String width);
}
