package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class UsuarioControlador {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/signup")
    public ResponseEntity<Object> registrar(@RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password) {
        // Validación de campos
        if (StringUtils.isEmpty(nombreUsuario) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return ResponseEntity.badRequest().body("Falta información");
        }

        // Verificar si el correo electrónico ya está registrado
        if (usuarioRepositorio.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico corresponde a un usuario registrado");
        }


        // Asignar roles en función de la lógica de negocios
        Roles rol = obtenerRol(email);

        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, password);
        nuevoUsuario.addRol(rol);
        usuarioRepositorio.save(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Método para obtener el rol en función del correo electrónico
    private Roles obtenerRol(String email) {
        if ("sebasfedele@gmail.com".equals(email)) {
            return Roles.ADMIN;
        } else {
            return Roles.USUARIO;
        }
    }
}

