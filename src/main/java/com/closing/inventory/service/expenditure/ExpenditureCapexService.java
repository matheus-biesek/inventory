package com.closing.inventory.service.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureCapex;
import com.closing.inventory.model.expenditure.ExpenditureSend;
import com.closing.inventory.model.material.Material;
import com.closing.inventory.repository.expenditure.ExpenditureCapexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import com.closing.inventory.service.material.MaterialMovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenditureCapexService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialMovementsService materialMovementsService;

    @Autowired
    private ExpenditureCapexRepository expenditureCapexRepository;

    public String create(ExpenditureSend expenditureSend) {
        if (materialRepository.existsByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth())) {
            Material materialFound = materialRepository.findByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth());
            ExpenditureCapex expenditureCapex = new ExpenditureCapex(materialFound, expenditureSend.getValue(), expenditureSend.getObservation(), expenditureSend.getAmount());

            materialFound.setAmount(materialFound.getAmount() + expenditureSend.getAmount());
            materialRepository.save(materialFound);
            materialMovementsService.registerMovements(materialFound, expenditureSend.getAmount());
            expenditureCapexRepository.save(expenditureCapex);
            return "Despesa do tipo capex adicionada com sucesso!";
        }
        return "O material informado não está registrado no banco de dados.\nCertifique-se de que este objeto está cadastrado corretamente!";
    }

    public String stringListMaterial(Material materialFound) {
        List<ExpenditureCapex> listCapex = expenditureCapexRepository.findByMaterial(materialFound);
        String expenditureCapex = listCapex.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "-----------------------------------\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getAmount() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Material: " + materialFound.getName() + " Tamanho: " + materialFound.getSize() + " Cm, Largura: " + materialFound.getWidth() + "Mm\nDepesa do tipo capex.\n" + expenditureCapex;
    }

    public String stringListAll() {
        List<ExpenditureCapex> listExpenditureCapex = expenditureCapexRepository.findAll();
        return listExpenditureCapex.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "Material: " + capex.getMaterial().getName() + " Tamanho: " + capex.getMaterial().getSize() + " Cm, Largura: " + capex.getMaterial().getWidth() + "Mm\nDepesa do tipo Capex.\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getAmount() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String stringListNonRepeatingEntity() {
        List<ExpenditureCapex> expenditureCapexList = expenditureCapexRepository.findAllNonRepeatingEntities();
        return expenditureCapexList.stream()
                .sorted(Comparator.comparing(ExpenditureCapex::getLocalDateTime).reversed())
                .map(capex -> "Material: " + capex.getMaterial().getName() + " Tamanho: " + capex.getMaterial().getSize() + " Cm, Largura: " + capex.getMaterial().getWidth() + "Mm\nDepesa do tipo Capex.\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
