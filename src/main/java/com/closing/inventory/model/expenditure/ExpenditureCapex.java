package com.closing.inventory.model.expenditure;

import com.closing.inventory.model.father.Expenditure;
import com.closing.inventory.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "expenditure_capex")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenditureCapex extends Expenditure {

    @ManyToOne
    @JoinColumn(name = "material_uuid", nullable = false)
    private Material material;

    public ExpenditureCapex(Material materialFound, BigDecimal value, String observation, int amount) {
        this.material = materialFound;
        setValue(value);
        setObservation(observation);
        setAmount(amount);
        setLocalDateTime(LocalDateTime.now());
    }
}
