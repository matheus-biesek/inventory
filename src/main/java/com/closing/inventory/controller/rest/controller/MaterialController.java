package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.dto.MaterialMovementsRequestDTO;
import com.closing.inventory.dto.MaterialRequestDTO;
import com.closing.inventory.service.material.MaterialMovementsService;
import com.closing.inventory.service.material.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;
    private final MaterialMovementsService materialMovementsService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock() {
        return this.materialService.stringListStock();
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody MaterialRequestDTO body) {
        return this.materialService.create(body);
    }

    @PostMapping("/string-list-movements-of-material-send")
    public @ResponseBody String stringListMovementsOfMaterialSend(@RequestBody MaterialRequestDTO body) {
        return this.materialService.stringListMovements(body);
    }

    @PostMapping("/remove-quantity")
    public @ResponseBody String removeQuantity(@RequestBody MaterialMovementsRequestDTO body) {
        return this.materialService.removeQuantity(body);
    }

    @GetMapping("/string-list-all-movements")
    public @ResponseBody String stringListAllMovements() {
        return this.materialMovementsService.stringListAll();
    }
}
