package com.closing.inventory.repository.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureOpex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExpenditureOpexRepository extends JpaRepository<ExpenditureOpex, UUID> {

    List<ExpenditureOpex> findByNameAndWidth(String name, String width);

    boolean existsByNameAndWidth(String name, String width);

    List<ExpenditureOpex> findAllByLocalDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT e FROM ExpenditureOpex e " +
            "WHERE e.id IN ( " +
            "    SELECT MIN(e2.id) FROM ExpenditureOpex e2 " +
            "    GROUP BY e2.name, e2.width " +
            ")")
    List<ExpenditureOpex> findAllNonRepeatingEntities();
}
