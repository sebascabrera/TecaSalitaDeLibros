package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.UsuarioDTO;
import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.security.service.util.JwtUtil;
import com.salitadelibros.salita.security.service.MyUserDetailsService;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class UsuarioControlador {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsService userDetailsService;
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

        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(password);

        // Asignar roles en función de la lógica de negocios
        Roles rol = obtenerRol(email);

        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, encodedPassword);
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
    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestParam String email, @RequestParam String password, @RequestParam String token) {
        // Validación de campos
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(token) || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Falta información");
        }

        // Realizar autenticación
        boolean authenticated = performAuthentication(email, password);
        if (authenticated) {
            // Cargar detalles del usuario
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(email);
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            // Validar el token
            if (jwtUtil.isValidToken(token, userDetails)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no corresponde al usuario");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fallo la autenticación");
        }
    }

    private boolean performAuthentication(String email, String password) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        return usuario != null && passwordEncoder.matches(password, usuario.getPassword());
    }
}


