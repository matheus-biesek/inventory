package com.closing.inventory.repository.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureCapex;
import com.closing.inventory.model.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExpenditureCapexRepository extends JpaRepository<ExpenditureCapex, UUID> {

    List<ExpenditureCapex> findAllByLocalDateTimeBetween(LocalDateTime from, LocalDateTime to);

    List<ExpenditureCapex> findByMaterial(Material material);

    @Query("SELECT e FROM ExpenditureCapex e " +
            "WHERE e.id IN ( " +
            "    SELECT MIN(e2.id) FROM ExpenditureCapex e2 " +
            "    GROUP BY e2.material.name, e2.material.size, e2.material.width " +
            ")")
    List<ExpenditureCapex> findAllNonRepeatingEntities();
}
