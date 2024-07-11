package com.closing.inventory.controller.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class test {


    @GetMapping("/um")
    public @ResponseBody String um() {
        return "Teste";
    }

}
