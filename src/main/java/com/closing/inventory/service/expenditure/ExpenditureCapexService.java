package com.closing.inventory.service.expenditure;

import com.closing.inventory.dto.ExpenditureRequestDTO;
import com.closing.inventory.model.expenditure.ExpenditureCapex;
import com.closing.inventory.model.material.Material;
import com.closing.inventory.repository.expenditure.ExpenditureCapexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import com.closing.inventory.service.material.MaterialMovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenditureCapexService {

    private final MaterialRepository materialRepository;
    private final MaterialMovementsService materialMovementsService;
    private final ExpenditureCapexRepository expenditureCapexRepository;

    public String create(ExpenditureRequestDTO body) {
        if (this.materialRepository.existsByNameAndWidth(body.name(), body.width())) {
            Material materialFound = this.materialRepository.findByNameAndWidth(body.name(), body.width());
            BigDecimal valueConverted = new BigDecimal(body.value().replace(",", "."));
            BigDecimal sizeConverted = new BigDecimal(body.size().replace(",", "."));
            ExpenditureCapex expenditureCapex = new ExpenditureCapex(materialFound, valueConverted, sizeConverted, body.observation());
            materialFound.setSize(materialFound.getSize().add(sizeConverted));
            this.materialRepository.save(materialFound);
            this.materialMovementsService.registerMovements(materialFound, sizeConverted);
            this.expenditureCapexRepository.save(expenditureCapex);
            return "Despesa do tipo capex adicionada com sucesso!";
        }
        return "O material informado não está registrado no banco de dados.\nCertifique-se de que este objeto está cadastrado corretamente!";
    }

    public String stringListMaterial(Material materialFound) {
        List<ExpenditureCapex> listCapex = this.expenditureCapexRepository.findByMaterial(materialFound);
        String expenditureCapex = listCapex.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "-----------------------------------\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getSize() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Material: " + materialFound.getName() + ", Largura: " + materialFound.getWidth() + "Mm\nDepesa do tipo capex.\n" + expenditureCapex;
    }

    public String stringListAll() {
        List<ExpenditureCapex> listExpenditureCapex = this.expenditureCapexRepository.findAll();
        return listExpenditureCapex.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "Material: " + capex.getMaterial().getName() + ", Largura: " + capex.getMaterial().getWidth() + "Mm\nDepesa do tipo Capex.\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getSize() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String stringListNonRepeatingEntity() {
        List<ExpenditureCapex> expenditureCapexList = this.expenditureCapexRepository.findAllNonRepeatingEntities();
        return expenditureCapexList.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "Material: " + capex.getMaterial().getName() + ", Largura: " + capex.getMaterial().getWidth() + "Mm\nDepesa do tipo Capex.\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
