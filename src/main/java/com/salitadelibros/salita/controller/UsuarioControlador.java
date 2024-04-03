package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@RestController
@RequestMapping("/auth")
public class UsuarioControlador {
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, passwordEncoder.encode(password));
        nuevoUsuario.addRol(Roles.USUARIO);
        usuarioRepositorio.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/signin")
    public String signIn(@RequestBody Usuario usuario) {
        if (!StringUtils.isEmpty(usuario.getEmail()) && !StringUtils.isEmpty(usuario.getPassword())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                // El usuario ya está autenticado, redirigir a la página de ingreso
                return "redirect:/ingreso.html";
            }
        }
        // Manejar caso de credenciales inválidas o falta de datos
        return "redirect:/error.html";
    }
}


        /*
        // Asignar roles en función de la lógica de negocios
        Roles rol = obtenerRol(email);
        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, password);
        nuevoUsuario.addRol(rol);
        usuarioRepositorio.save(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }*/

    /*
    // Método para obtener el rol en función del correo electrónico
    private Roles obtenerRol(String email) {
        if ("sebasfedele@gmail.com".equals(email)) {
            return Roles.ADMIN;
        } else {
            return Roles.USUARIO;
        }
    }*/


