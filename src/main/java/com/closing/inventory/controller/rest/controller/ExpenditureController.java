package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.dto.ExpenditureRequestDTO;
import com.closing.inventory.service.expenditure.ExpenditureCapexService;
import com.closing.inventory.service.expenditure.ExpenditureOpexService;
import com.closing.inventory.service.expenditure.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-expenditure")
@RequiredArgsConstructor
public class ExpenditureController {

    private final ExpenditureService expenditureService;
    private final ExpenditureOpexService expenditureOpexService;
    private final ExpenditureCapexService expenditureCapexService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock(){
        return this.expenditureService.stringListStock();
    }

    @PostMapping("/create-capex")
    public @ResponseBody String createCapex(@RequestBody ExpenditureRequestDTO body) {
        return this.expenditureCapexService.create(body);
    }

    @PostMapping("/create-opex")
    public @ResponseBody String createOpex(@RequestBody ExpenditureRequestDTO body) {
        return this.expenditureOpexService.create(body);
    }

    @PostMapping("string-list-of-expenditure-send")
    public @ResponseBody String stringListOfExpenditureSend(@RequestBody ExpenditureRequestDTO body){
        return this.expenditureService.stringList(body);
    }

    @GetMapping("/string-list-all")
    public @ResponseBody String stringListAll(){
        return this.expenditureService.stringListAll();
    }
}
