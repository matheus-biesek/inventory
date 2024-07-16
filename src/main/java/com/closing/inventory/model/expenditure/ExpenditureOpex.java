package com.closing.inventory.model.expenditure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "expenditure_opex")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenditureOpex {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String width;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private BigDecimal size;

    @Column(length = 1000)
    private String observation;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    public ExpenditureOpex(String name, String width, BigDecimal valueConverted, BigDecimal sizeConverted, String observation) {
        this.name = name;
        this.width = width;
        this.value = valueConverted;
        this.size = sizeConverted;
        this.observation = observation;
        this.localDateTime = LocalDateTime.now();
    }
}
