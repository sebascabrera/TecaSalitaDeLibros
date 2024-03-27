package com.salitadelibros.salita.security.service;

import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + nombreUsuario);
        }
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
