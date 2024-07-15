package com.closing.inventory.model.material;

import com.closing.inventory.model.father.MovementsHistoric;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "material_movements")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialMovements extends MovementsHistoric {

    @ManyToOne
    @JoinColumn(name = "material_uuid", nullable = false)
    private Material material;

    public MaterialMovements(Material material, LocalDateTime localDateTime, double movementsAmount) {
        super(localDateTime, movementsAmount);
        this.material = material;
    }

}
