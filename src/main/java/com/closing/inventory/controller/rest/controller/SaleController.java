package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.model.sale.Sale;
import com.closing.inventory.service.product.ProductService;
import com.closing.inventory.service.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
;

@RestController
@RequestMapping("/ipa-sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock(){
        return saleService.StringListStock();
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody Sale saleSend ) {
        return saleService.create(saleSend);
    }

    @PostMapping("/string-list-sale-of-product-send")
    public @ResponseBody String stringListSaleOProductSend(@RequestBody Product productSend) {
        return productService.stringListAllSale(productSend);
    }

    @GetMapping("/string-list-all")
    public String stringListAll(){
        return saleService.StringListAll();
    }
}
