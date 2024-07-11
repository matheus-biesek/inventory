package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.service.analyze.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequestMapping("/ipa-analyze")
public class AnalyseController {

    @Autowired
    private AnalyzeService analyzeService;

    @GetMapping("/list-sale-months")
    public @ResponseBody BigDecimal[] collectSale() {
        return analyzeService.listSaleMonths();
    }

    @GetMapping("/list-capex-months")
    public @ResponseBody BigDecimal[] collectCapex() {
        return analyzeService.listCapexMonths();
    }

    @GetMapping("/list-opex-months")
    public @ResponseBody BigDecimal[] collectOpex() {
        return analyzeService.listOpexMonths();
    }

    @GetMapping("/list-expenditure-months")
    public @ResponseBody BigDecimal[] collectAllExpenditure() {
        return analyzeService.listExpendituresMonths();
    }

    @GetMapping("list-profit-months")
    public @ResponseBody BigDecimal[] collectProfit() {
        return analyzeService.listProfitMonths();
    }
}
