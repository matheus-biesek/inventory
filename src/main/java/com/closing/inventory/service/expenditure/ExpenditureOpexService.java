package com.closing.inventory.service.expenditure;

import com.closing.inventory.dto.ExpenditureRequestDTO;
import com.closing.inventory.model.expenditure.ExpenditureOpex;
import com.closing.inventory.repository.expenditure.ExpenditureOpexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenditureOpexService {

    private final ExpenditureOpexRepository expenditureOpexRepository;
    private final MaterialRepository materialRepository;

    public String create(ExpenditureRequestDTO body) {
        if (this.materialRepository.existsByNameAndWidth(body.name(), body.width())) return "Erro!\nDespesa to tipo CAPEX.";
        BigDecimal valueConverted = new BigDecimal(body.value().replace(",", "."));
        BigDecimal sizeConverted = new BigDecimal(body.size().replace(",", "."));
        ExpenditureOpex expenditureOpex = new ExpenditureOpex(body.name(), body.width(), valueConverted, sizeConverted, body.observation());
        this.expenditureOpexRepository.save(expenditureOpex);
        return "Despesa do tipo opex adicionada com sucesso!";
    }

    public String stringListOpex(ExpenditureRequestDTO body) {
        List<ExpenditureOpex> listOpex = this.expenditureOpexRepository.findByNameAndWidth(body.name(), body.width());
        String expenditureOpex = listOpex.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(capex -> "-----------------------------------\nValor gasto: R$" + capex.getValue() + "\nQtd: " + capex.getSize() + " Un\nObservação: " + capex.getObservation() + "\nData: " + capex.getLocalDateTime())
                .collect(Collectors.joining("\n"));
        return "Despesa: " + body.name() + " Largura: " + body.width() + "Mm\nDepesa do tipo Opex.\n" + expenditureOpex;
    }

    public String stringListAll() {
        List<ExpenditureOpex> expenditureOpexFound = this.expenditureOpexRepository.findAll();
        return expenditureOpexFound.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(opex -> "Despesa: " + opex.getName() + " Largura: " + opex.getWidth() + "Mm\nDepesa do tipo Opex.\nValor gasto: R$" + opex.getValue() + "\nQtd: " + opex.getSize() + " Un\nObservação: " + opex.getObservation() + "\nData: " + opex.getLocalDateTime() + "\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }

    public String stringListNonRepeatingEntity() {
        List<ExpenditureOpex> expenditureOpexList = this.expenditureOpexRepository.findAllNonRepeatingEntities();
        return expenditureOpexList.stream()
                .sorted(Comparator.comparing(ExpenditureOpex::getLocalDateTime).reversed())
                .map(opex -> "Despesa: " + opex.getName() + " Largura: " + opex.getWidth() + "Mm\nDepesa do tipo Opex.\n-----------------------------------")
                .collect(Collectors.joining("\n"));
    }
}
