package com.closing.inventory.model.product;

import com.closing.inventory.model.father.InventoryItems;

import com.closing.inventory.model.sale.Sale;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Table(name = "product")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends InventoryItems {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductMovements> productMovements;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Sale> saleList;

    public Product(String name, String size, String width, int amount) {
        setName(name);
        setSize(size);
        setWidth(width);
        setAmount(amount);
        this.productMovements = Collections.emptyList();
        this.saleList = Collections.emptyList();
    }

}
