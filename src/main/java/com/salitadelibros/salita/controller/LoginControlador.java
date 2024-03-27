package com.salitadelibros.salita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/sig-in")
public class LoginControlador {
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping
    public String showLoginPage() {
        return "redirect:/index.html";
    }
    @PostMapping("/auth/signin")
    public ResponseEntity<String> signin(@RequestParam String email, @RequestParam String password) {
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            // Si la autenticación tiene éxito
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("/web/ingreso/ingreso.html");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/error");
        }
    }
}

