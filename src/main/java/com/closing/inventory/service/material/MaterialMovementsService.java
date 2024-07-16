package com.closing.inventory.service.material;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.material.MaterialMovements;
import com.closing.inventory.model.user.User;
import com.closing.inventory.repository.material.MaterialMovementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialMovementsService {

    private final MaterialMovementsRepository materialMovementsRepository;

    public void registerMovements(Material material, BigDecimal quantity, User user){
        MaterialMovements materialMovements = new MaterialMovements(material, quantity, user);
        this.materialMovementsRepository.save(materialMovements);
    }

    public List<MaterialMovements> findListMaterial(Material material) {
        return this.materialMovementsRepository.findByMaterial(material);
    }

    public String stringListAll(){
        List<MaterialMovements> materialMovements = this.materialMovementsRepository.findAll();
        return materialMovements.stream()
                .sorted(Comparator.comparing(MaterialMovements::getLocalDateTime).reversed())
                .map(mov ->  "Material: " + mov.getMaterial().getName() + ", Largura: " + mov.getMaterial().getWidth() + "Mm\nMovimentação: " + mov.getSize() + "\nData: " + mov.getLocalDateTime() + "\nUsúario: " + mov.getUser().getUsername() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
