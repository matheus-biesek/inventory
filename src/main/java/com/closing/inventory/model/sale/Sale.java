package com.closing.inventory.model.sale;

import com.closing.inventory.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "sale")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_uuid", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false, precision = 10, scale = 2)
    private int amount;

    @Column(length = 1000)
    private String observation;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    public Sale(Product product, BigDecimal value, int amount, String observation) {
        this.product = product;
        this.value = value;
        this.amount = amount;
        this.observation = observation;
        this.localDateTime = LocalDateTime.now();
    }
}
