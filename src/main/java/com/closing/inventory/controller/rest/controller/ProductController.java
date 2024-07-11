package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.model.product.Product;
import com.closing.inventory.service.product.ProductMovementsService;
import com.closing.inventory.service.product.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMovementsService productMovementsService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock() {
        return productService.stringListStock();
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody Product productSend) {
        return productService.create(productSend);
    }

    @PostMapping("/string-list-movements-of-product-send")
    public @ResponseBody String stringListMovementsOfProductSend(@RequestBody Product productSend) {
        return productService.stringListMovements(productSend);
    }

    @PostMapping("/append-quantity")
    public @ResponseBody String appendQuantity(@RequestBody Product productSend) {
        return productService.appendQuantity(productSend);
    }

    @GetMapping("/string-list-all-movements")
    public @ResponseBody String stringListAllMovements() {
        return productMovementsService.stringListAll();
    }
}
