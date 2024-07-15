package com.closing.inventory.service.material;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.material.MaterialMovements;
import com.closing.inventory.repository.material.MaterialMovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialMovementsService {

    @Autowired
    MaterialMovementsRepository materialMovementsRepository;

    public void registerMovements(Material material, double amount){
        MaterialMovements materialMovements = new MaterialMovements(material, LocalDateTime.now(), amount);
        materialMovementsRepository.save(materialMovements);
    }

    public List<MaterialMovements> findListMaterial(Material material) {
        return materialMovementsRepository.findByMaterial(material);
    }

    public String stringListAll(){
        List<MaterialMovements> materialMovements = materialMovementsRepository.findAll();
        return materialMovements.stream()
                .sorted(Comparator.comparing(MaterialMovements::getLocalDateTime).reversed())
                .map(mov ->  "Material: " + mov.getMaterial().getName() + " Tamanho: " + mov.getMaterial().getSize() + " Cm, Largura: " + mov.getMaterial().getWidth() + "Mm\nMovimentação: " + mov.getMovementsAmount() + "\nData: " + mov.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
