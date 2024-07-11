package com.closing.inventory.service.expenditure;

import com.closing.inventory.model.expenditure.ExpenditureSend;
import com.closing.inventory.model.material.Material;
import com.closing.inventory.repository.expenditure.ExpenditureOpexRepository;
import com.closing.inventory.repository.material.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenditureService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ExpenditureOpexRepository expenditureOpexRepository;

    @Autowired
    private ExpenditureCapexService expenditureCapexService;

    @Autowired
    private ExpenditureOpexService expenditureOpexService;

    public String stringList(ExpenditureSend expenditureSend) {
        if (materialRepository.existsByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth())) {
            Material materialFound = materialRepository.findByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth());
             return expenditureCapexService.stringListMaterial(materialFound);
        }
        if(expenditureOpexRepository.existsByNameAndSizeAndWidth(expenditureSend.getName(), expenditureSend.getSize(), expenditureSend.getWidth())){
            return expenditureOpexService.stringListOpex(expenditureSend);
        }
        return "Despesa do tipo opex não existe no banco de dados!";
    }

    public String stringListAll() {
        return expenditureOpexService.stringListAll() + "\n" + expenditureCapexService.stringListAll();
    }

    public String stringListStock() {
        return  expenditureOpexService.stringListNonRepeatingEntity()+ "\n" + expenditureCapexService.stringListNonRepeatingEntity();
    }
}
