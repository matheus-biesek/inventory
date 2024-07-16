package com.closing.inventory.model.product;

import com.closing.inventory.model.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Table(name = "product")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String width;

    @Column(nullable = false)
    private int amount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductMovements> productMovements;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Sale> saleList;

    public Product(String name, String size, String width, int amount) {
        this.name = name;
        this.size = size;
        this.width = width;
        this.amount = amount;
        this.productMovements = Collections.emptyList();
        this.saleList = Collections.emptyList();
    }
}
