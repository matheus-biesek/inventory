package com.closing.inventory.service.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureOpex;
import com.closing.inventory.model.expenditure.ExpenditureSend;
import com.closing.inventory.repository.expenditure.ExpenditureOpexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenditureOpexService {

    @Autowired
    private ExpenditureOpexRepository expenditureOpexRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public String create(ExpenditureSend expenditureSend) {
        if (materialRepository.existsByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth())){
            return "Não é possível realizar esta ação porque a despesa se refere a um material!";
        }
        ExpenditureOpex expenditureOpex = new ExpenditureOpex(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth(), expenditureSend.getValue(), expenditureSend.getObservation(), expenditureSend.getAmount());
        expenditureOpexRepository.save(expenditureOpex);
        return "Despesa do tipo opex adicionada com sucesso!";
    }

    public String stringListOpex(ExpenditureSend expenditureSend) {
        List<ExpenditureOpex> listOpex = expenditureOpexRepository.findByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth());
        String expenditureOpex = listOpex.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(capex -> "-----------------------------------\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getAmount() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Despesa: " + expenditureSend.getName() + " Tamanho: " + expenditureSend.getSize() + " Cm, Largura: " + expenditureSend.getWidth() + "Mm\nDepesa do tipo Opex.\n" + expenditureOpex;
    }

    public String stringListAll() {
        List<ExpenditureOpex> expenditureOpexFound = expenditureOpexRepository.findAll();
        return expenditureOpexFound.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(opex -> "Despesa: " + opex.getName() + " Tamanho: " + opex.getSize() + " Cm, Largura: " + opex.getWidth() + "Mm\nDepesa do tipo Opex.\nValor gasto: R$" + opex.getValue() + "\nQtd: " + opex.getAmount() + " Un\nObservação: " + opex.getObservation() + "\nData: " + opex.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String stringListNonRepeatingEntity() {
        List<ExpenditureOpex> expenditureOpexList = expenditureOpexRepository.findAllNonRepeatingEntities();
        return expenditureOpexList.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(opex -> "Despesa: " + opex.getName() + " Tamanho: " + opex.getSize() + " Cm, Largura: " + opex.getWidth() + "Mm\nDepesa do tipo Opex.\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
