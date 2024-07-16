package com.closing.inventory.model.material;

import com.closing.inventory.model.expenditure.ExpenditureCapex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "material")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String width;

    @Column(nullable = false)
    private BigDecimal size;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<MaterialMovements> materialMovements;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<ExpenditureCapex> listMaterialExpenditureCapex;

    public Material(String name, String width) {
        this.name = name;
        this.width = width;
        this.size = BigDecimal.ZERO;
    }
}
