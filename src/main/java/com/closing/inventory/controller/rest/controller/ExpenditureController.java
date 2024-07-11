package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.model.expenditure.ExpenditureSend;
import com.closing.inventory.service.expenditure.ExpenditureCapexService;
import com.closing.inventory.service.expenditure.ExpenditureOpexService;
import com.closing.inventory.service.expenditure.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-expenditure")
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @Autowired
    private ExpenditureOpexService expenditureOpexService;

    @Autowired
    private ExpenditureCapexService expenditureCapexService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock(){
        return expenditureService.stringListStock();
    }

    @PostMapping("/create-capex")
    public @ResponseBody String createCapex(@RequestBody ExpenditureSend expenditureSend) {
        return expenditureCapexService.create(expenditureSend);
    }

    @PostMapping("/create-opex")
    public @ResponseBody String createOpex(@RequestBody ExpenditureSend expenditureSend) {
        return expenditureOpexService.create(expenditureSend);
    }

    @PostMapping("string-list-of-expenditure-send")
    public @ResponseBody String stringListOfExpenditureSend(@RequestBody ExpenditureSend expenditureSend){
        return expenditureService.stringList(expenditureSend);
    }

    @GetMapping("/string-list-all")
    public @ResponseBody String stringListAll(){
        return expenditureService.stringListAll();
    }

}
