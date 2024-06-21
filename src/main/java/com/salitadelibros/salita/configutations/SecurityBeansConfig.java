package com.salitadelibros.salita.configutations;

import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String inputName) throws UsernameNotFoundException {
                System.out.println("loadUserByUsername: " + inputName);
                Usuario usuario = usuarioRepositorio.findByEmail(inputName);
                if (usuario != null) {
                    System.out.println("loadUserByUsername: usuario nulo");
                    if (usuario.getEmail().equals("salitadelibros@admin.com")) {
                        return new User(usuario.getEmail(), usuario.getPassword(),
                                AuthorityUtils.createAuthorityList("ADMIN"));
                    }
                    return new User(usuario.getEmail(), usuario.getPassword(),
                            AuthorityUtils.createAuthorityList("USUARIO"));
                } else {
                    System.out.println("loadUserByUsername: usuario desconocido");
                    throw new UsernameNotFoundException("Unknown user: " + inputName);
                }
            }
        };
    }
}

