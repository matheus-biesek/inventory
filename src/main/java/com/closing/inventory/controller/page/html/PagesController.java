package com.closing.inventory.controller.page.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/register-product")
    public String pageRegisterProduct() {
        return "register-product";
    }

    @GetMapping("/register-material")
    public String pageRegisterMaterial() {
        return "register-material";
    }

    @GetMapping("/expenditure")
    public String PageExpenditure() {
        return "expenditure";
    }

    @GetMapping("/sale")
    public String pageSale() {
        return "sale";
    }

    @GetMapping("/analyze")
    public String pageAnalyze() {
        return "analyze";
    }

    @GetMapping("/register-user")
    public String pageRegisterUser() { return "register-user"; }

    @GetMapping("/login-user")
    public String pageLoginUser() { return "login-user"; }
}
