package com.closing.inventory.model.father;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovementsHistoric {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private double movementsAmount;

    protected MovementsHistoric(LocalDateTime moveDate, double amount) {
        this.localDateTime = moveDate;
        this.movementsAmount = amount;
    }

}
