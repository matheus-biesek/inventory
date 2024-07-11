package com.closing.inventory.repository.material;

import com.closing.inventory.model.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {

    boolean existsByNameAndSizeAndWidth(String name, String size, String width);

    Material findByNameAndSizeAndWidth(String name, String size, String width);

}
