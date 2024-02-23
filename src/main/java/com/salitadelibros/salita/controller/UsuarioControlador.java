package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<Object> registrar(
            @RequestParam String nombreUsuario,
            @RequestParam String email,
            @RequestParam String password) {

        if (nombreUsuario.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Falta información", HttpStatus.FORBIDDEN);
        }
        if (usuarioRepositorio.findByEmail(email) != null) {
            return new ResponseEntity<>("El correo electrónico corresponde a un usuario registrado", HttpStatus.FORBIDDEN);
        }
        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, passwordEncoder.encode(password));
        // Asignar roles en función del correo electrónico
        if ("sebasfedele@gmail.com".equals(email)) {
            nuevoUsuario.addRol(Roles.ADMIN);
        } else {
            nuevoUsuario.addRol(Roles.USUARIO);
        }
        usuarioRepositorio.save(nuevoUsuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<Object> signIn(
            @RequestParam String email,
            @RequestParam String password) {
        boolean authenticated = performAuthentication(email, password);
        if (authenticated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Fallo la autenticación", HttpStatus.UNAUTHORIZED);
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

