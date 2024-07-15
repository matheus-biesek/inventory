package com.closing.inventory.repository.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureOpex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExpenditureOpexRepository extends JpaRepository<ExpenditureOpex, UUID> {

    List<ExpenditureOpex> findByNameAndSizeAndWidth(String name, String size, String width);

    boolean existsByNameAndSizeAndWidth(String name, String size, String width);

    List<ExpenditureOpex> findAllByLocalDateTimeBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT e FROM ExpenditureOpex e " +
            "WHERE e.id IN ( " +
            "    SELECT MIN(e2.id) FROM ExpenditureOpex e2 " +
            "    GROUP BY e2.name, e2.size, e2.width " +
            ")")
    List<ExpenditureOpex> findAllNonRepeatingEntities();
}
