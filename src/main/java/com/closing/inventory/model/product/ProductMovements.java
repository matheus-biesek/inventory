package com.closing.inventory.model.product;

import com.closing.inventory.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "product_movements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMovements {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "product_uuid", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    public ProductMovements(Product product, int amount, LocalDateTime localDateTime, User user) {
        this.product = product;
        this.amount = amount;
        this.localDateTime = localDateTime;
        this.user = user;
    }
}
