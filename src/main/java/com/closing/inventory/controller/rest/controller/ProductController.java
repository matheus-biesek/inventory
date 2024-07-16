package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.dto.ProductMovementsRequestDTO;
import com.closing.inventory.dto.ProductRequestDTO;
import com.closing.inventory.service.product.ProductMovementsService;
import com.closing.inventory.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMovementsService productMovementsService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock() {
        return this.productService.stringListStock();
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody ProductRequestDTO body) {
        return this.productService.create(body);
    }

    @PostMapping("/string-list-movements-of-product-send")
    public @ResponseBody String stringListMovementsOfProductSend(@RequestBody ProductRequestDTO body) {
        return this.productService.stringListMovements(body);
    }

    @PostMapping("/add-quantity")
    public @ResponseBody String addQuantity(@RequestBody ProductMovementsRequestDTO body) {
        return this.productService.addQuantity(body);
    }

    @GetMapping("/string-list-all-movements")
    public @ResponseBody String stringListAllMovements() {
        return this.productMovementsService.stringListAll();
    }
}
