package com.closing.inventory.repository.material;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.material.MaterialMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaterialMovementsRepository extends JpaRepository<MaterialMovements, Integer> {

    List<MaterialMovements> findByMaterial(Material material);
}
