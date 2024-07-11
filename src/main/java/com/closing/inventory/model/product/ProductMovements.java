package com.closing.inventory.model.product;

import com.closing.inventory.model.father.MovementsHistoric;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "product_movements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMovements extends MovementsHistoric {

    @ManyToOne
    @JoinColumn(name = "product_uuid", nullable = false)
    private Product product;

    public ProductMovements(Product product, LocalDateTime localDateTime, int movementsAmount) {
        super(localDateTime, movementsAmount);
        this.product = product;
    }
}
