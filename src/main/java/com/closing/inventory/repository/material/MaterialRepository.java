package com.closing.inventory.repository.material;

import com.closing.inventory.model.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {

    boolean existsByNameAndWidth(String name, String width);

    Material findByNameAndWidth(String name, String width);

}
