package com.closing.inventory.service.material;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.material.MaterialMovements;
import com.closing.inventory.repository.material.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMovementsService materialMovementsService;

    public String stringListStock(){
        List<Material> listMaterials = materialRepository.findAll();
        if(listMaterials.isEmpty()){
            return "Não há materiais no estoque!";
        }
        return listMaterials.stream()
                .sorted(Comparator.comparing(Material::getName))
                .map(mat -> "Material: " + mat.getName() + ", Tamanho " + mat.getSize() + " Cm, Largura: " + mat.getWidth() + ", Mm\nQtd Atual: " + mat.getAmount() + " Un\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String create(Material materialSend) {
        if (materialRepository.existsByNameAndSizeAndWidth(materialSend.getName(), materialSend.getSize(), materialSend.getWidth())) {
            return "O material já existe no banco de dados!";
        }
        materialRepository.save(materialSend);
        materialMovementsService.registerMovements(materialSend, 0);
        return "Material adicionado com sucesso";
    }

    public String stringListMovements(Material material){
        Material materialFound = materialRepository.findByNameAndSizeAndWidth(material.getName(), material.getSize(), material.getWidth());
        if(materialFound == null){
            return "O material encontrado está nulo no banco de dados!";
        }
        List<MaterialMovements> movements = materialMovementsService.findListMaterial(materialFound);
        String movementsString = movements.stream()
                .sorted(Comparator.comparing(MaterialMovements::getLocalDateTime).reversed())
                .map(mov -> "-----------------------------------\nData: " + mov.getLocalDateTime() + " - Qtd: " + mov.getMovementsAmount())
                .collect(Collectors.joining("\n"));
        return "Material: " + materialFound.getName() + ", Tamanho: " + materialFound.getSize() + "Cm, Largura: " + materialFound.getWidth() + "Mm\n Qtd atual: " + materialFound.getAmount() + "Un\n" + "Histórico de movimentações:\n" + movementsString;
    }

    public String appendQuantity(Material materialSend) {
        Material materialFound = materialRepository.findByNameAndSizeAndWidth(materialSend.getName(), materialSend.getSize(), materialSend.getWidth());
        if(materialFound == null){
            return "O material encontrado está nulo no banco de dados!";
        }
        materialFound.setAmount(materialFound.getAmount() + materialSend.getAmount());
        materialRepository.save(materialFound);
        materialMovementsService.registerMovements(materialFound, materialSend.getAmount());
        return "Movimentação concluida com sucesso!";
    }
}
