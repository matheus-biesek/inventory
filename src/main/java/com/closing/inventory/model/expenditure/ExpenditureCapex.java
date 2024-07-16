package com.closing.inventory.model.expenditure;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "expenditure_capex")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenditureCapex {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "material_uuid", nullable = false)
    private Material material;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private BigDecimal size;

    @Column(length = 1000)
    private String observation;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;


    public ExpenditureCapex(Material material, BigDecimal valueConverted, BigDecimal sizeConverted, String observation, User user) {
        this.material = material;
        this.value = valueConverted;
        this.size = sizeConverted;
        this.observation = observation;
        this.localDateTime = LocalDateTime.now();
        this.user = user;
    }
}
