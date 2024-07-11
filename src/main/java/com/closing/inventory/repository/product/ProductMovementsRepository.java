package com.closing.inventory.repository.product;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.product.ProductMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Integer> {

    List<ProductMovements> findByProduct(Product product);
}
