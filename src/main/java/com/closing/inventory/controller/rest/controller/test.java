package com.closing.inventory.controller.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teste")
public class test {


    @GetMapping("/um")
    public ResponseEntity<Map<String, String>> getTeste() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Api privada");
        return ResponseEntity.ok(response);
    }

}
