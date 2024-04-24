package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UsuarioControlador {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Object> registrar(@RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password) {
        // Valida campos
        if (nombreUsuario.isBlank() || email.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body("Falta información");
        }
        // Verifica correo electrónico
        if (usuarioRepositorio.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico corresponde a un usuario registrado");
        }
        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, passwordEncoder.encode(password));
        nuevoUsuario.addRol(Roles.USUARIO);
        usuarioRepositorio.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email").toLowerCase();
        String password = credentials.get("password");
        if (email.isBlank() || password.isBlank()) {
            throw new BadCredentialsException("Credenciales inválidas mail o contraseña en blanco");
        }
        try {
            System.out.println("Autenticando: email=" + email + ", password=" + password);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/ingreso.html")).build();
        } catch (AuthenticationException e) {
            // autenticación falló
            System.out.println("Autenticación fallida para: email=" + email);
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }
    /*
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        if(request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return ResponseEntity.status(HttpStatus.OK).body("Sesión cerrada exitosamente");
    }*/

}



