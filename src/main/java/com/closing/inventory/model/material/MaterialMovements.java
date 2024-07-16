package com.closing.inventory.model.material;

import com.closing.inventory.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "material_movements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialMovements{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "material_uuid", nullable = false)
    private Material material;

    @Column(nullable = false)
    private BigDecimal size;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    public MaterialMovements(Material material, BigDecimal quantity, User user) {
        this.material = material;
        this.size = quantity;
        this.localDateTime = LocalDateTime.now();
        this.user = user;
    }
}
