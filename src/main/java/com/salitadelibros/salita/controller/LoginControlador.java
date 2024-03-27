package com.salitadelibros.salita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sig-in")
public class LoginControlador {

    @GetMapping
    public String showLoginPage() {
        return "index";
    }
}

