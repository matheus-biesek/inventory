package com.closing.inventory.service.material;

import com.closing.inventory.dto.MaterialMovementsRequestDTO;
import com.closing.inventory.dto.MaterialRequestDTO;
import com.closing.inventory.model.material.Material;
import com.closing.inventory.model.material.MaterialMovements;
import com.closing.inventory.repository.material.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMovementsService materialMovementsService;

    public String stringListStock() {
        List<Material> listMaterials = this.materialRepository.findAll();
        if (listMaterials.isEmpty()) return "Não há materiais no estoque!";
        return listMaterials.stream()
                .sorted(Comparator.comparing(Material::getName))
                .map(mat -> "Material: " + mat.getName() + ", Largura: " + mat.getWidth() + " Mm\n" + "Tamanho atual: " + mat.getSize() + " Un\n" + "-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String create(MaterialRequestDTO body) {
        if (this.materialRepository.existsByNameAndWidth(body.name(), body.width())) return "O material já existe no banco de dados!";
        Material material = new Material(body.name(), body.width());
        this.materialRepository.save(material);
        this.materialMovementsService.registerMovements(material, BigDecimal.ZERO);
        return "Material adicionado com sucesso";
    }

    public String stringListMovements(MaterialRequestDTO body) {
        Material materialFound = this.materialRepository.findByNameAndWidth(body.name(), body.width());
        if (materialFound == null) return "O material não existe no banco de dados!";
        List<MaterialMovements> movements = this.materialMovementsService.findListMaterial(materialFound);
        String movementsString = movements.stream()
                .sorted(Comparator.comparing(MaterialMovements::getLocalDateTime).reversed())
                .map(mov -> "-----------------------------------\nData: " + mov.getLocalDateTime() + " - Qtd: " + mov.getSize())
                .collect(Collectors.joining("\n"));
        return "Material: " + materialFound.getName() + ", Largura: " + materialFound.getWidth() + " Mm\n" + "Tamanho atual: " + materialFound.getSize() + " M\n" + "Histórico de movimentações:\n" + movementsString;
    }

    public String removeQuantity(MaterialMovementsRequestDTO body) {
        Material materialFound = this.materialRepository.findByNameAndWidth(body.name(), body.width());
        if (materialFound == null) return "O material não existe no banco de dados!";
        BigDecimal quantityConverted = new BigDecimal(body.quantity().replace(",", "."));
        if (materialFound.getSize().compareTo(quantityConverted) < 0) {
            return "Não é possível realizar esta ação!\nO material encontrado possui: " + materialFound.getSize() + " Un.\nVocê está querendo tirar: " + quantityConverted + " Un.\nOu seja:\nA quantidade do material ficará negativa.";
        }
        materialFound.setSize(materialFound.getSize().subtract(quantityConverted));
        this.materialRepository.save(materialFound);
        this.materialMovementsService.registerMovements(materialFound, quantityConverted.negate());
        return "Movimentação concluída com sucesso!";
    }
}
