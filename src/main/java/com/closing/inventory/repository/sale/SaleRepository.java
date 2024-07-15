package com.closing.inventory.repository.sale;


import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

    List<Sale> findByProduct(Product product);

    List<Sale> findAllByLocalDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT e FROM Sale e " +
            "WHERE e.id IN ( " +
            "    SELECT MIN(e2.id) FROM Sale e2 " +
            "    GROUP BY e2.product.name, e2.product.size, e2.product.width " +
            ")")
    List<Sale> findAllNonRepeatingEntities();
}
