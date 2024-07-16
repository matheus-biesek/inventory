package com.closing.inventory.model.user;

import com.closing.inventory.model.material.MaterialMovements;
import com.closing.inventory.model.product.ProductMovements;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany
    private List<MaterialMovements> materialMovements;

    @OneToMany
    private List<ProductMovements> productMovements;
}
