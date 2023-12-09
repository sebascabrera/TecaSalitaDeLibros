package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioControlador {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String nombreUsuario,
            @RequestParam String email,
            @RequestParam String password) {

        if (nombreUsuario.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (usuarioRepositorio.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        // Crear un nuevo usuario y asignar roles
            Usuario nuevoUsuario = new Usuario(nombreUsuario, email, passwordEncoder.encode(password));
        nuevoUsuario.addRol("USUARIO");
        usuarioRepositorio.save(nuevoUsuario);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<Object> signIn(
            @RequestParam String email,
            @RequestParam String password) {

        // Realizar la autenticación (ejemplo básico)
        boolean authenticated = performAuthentication(email, password);

        if (authenticated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean performAuthentication(String email, String password) {
        // Lógica de autenticación básica
        // Compara las credenciales con la base de datos u otro mecanismo de almacenamiento seguro
        // Devuelve true si las credenciales son válidas, false en caso contrario

        Usuario usuario = usuarioRepositorio.findByEmail(email);

        return usuario != null && passwordEncoder.matches(password, usuario.getPassword());
    }

}

