package com.closing.inventory.service.expenditure;

import com.closing.inventory.dto.ExpenditureRequestDTO;
import com.closing.inventory.model.material.Material;
import com.closing.inventory.repository.expenditure.ExpenditureOpexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final MaterialRepository materialRepository;
    private final ExpenditureOpexRepository expenditureOpexRepository;
    private final ExpenditureCapexService expenditureCapexService;
    private final ExpenditureOpexService expenditureOpexService;

    public String stringList(ExpenditureRequestDTO body) {
        if (this.materialRepository.existsByNameAndWidth(body.name(), body.width())) {
             Material materialFound = this.materialRepository.findByNameAndWidth(body.name(), body.width());
             return this.expenditureCapexService.stringListMaterial(materialFound);
        }
        if(this.expenditureOpexRepository.existsByNameAndWidth(body.name(), body.width())){
            return this.expenditureOpexService.stringListOpex(body);
        }
        return "Despesa do tipo opex não existe no banco de dados!";
    }

    public String stringListAll() {
        return this.expenditureOpexService.stringListAll() + "\n" + this.expenditureCapexService.stringListAll();
    }

    public String stringListStock() {
        return  this.expenditureOpexService.stringListNonRepeatingEntity()+ "\n" + this.expenditureCapexService.stringListNonRepeatingEntity();
    }
}
