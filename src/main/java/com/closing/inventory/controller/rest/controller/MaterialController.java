package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.model.material.Material;
import com.closing.inventory.service.material.MaterialMovementsService;
import com.closing.inventory.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipa-material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialMovementsService materialMovementsService;

    @GetMapping("/string-list-stock")
    public @ResponseBody String stringListStock() {
        return materialService.stringListStock();
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody Material materialSend) {
        return materialService.create(materialSend);
    }

    @PostMapping("/string-list-movements-of-material-send")
    public @ResponseBody String stringListMovementsOfMaterialSend(@RequestBody Material materialSend) {
        return materialService.stringListMovements(materialSend);
    }

    @PostMapping("/append-quantity")
    public @ResponseBody String appendQuantity(@RequestBody Material materialSend) {
        return materialService.appendQuantity(materialSend);
    }

    @GetMapping("/string-list-all-movements")
    public @ResponseBody String stringListAllMovements() {
        return materialMovementsService.stringListAll();
    }
}
