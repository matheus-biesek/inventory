package com.closing.inventory.model.expenditure;

import com.closing.inventory.model.father.Expenditure;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "expenditure_opex")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenditureOpex extends Expenditure {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String width;

    public ExpenditureOpex(String name, String size, String width, BigDecimal value, String observation, int amount) {
        this.name = name;
        this.size = size;
        this.width = width;
        setValue(value);
        setObservation(observation);
        setAmount(amount);
        setLocalDateTime(LocalDateTime.now());
    }
}
