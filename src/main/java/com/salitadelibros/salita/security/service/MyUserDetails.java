package com.salitadelibros.salita.security.service;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private String nombreUsuario;
    private String password;
    private boolean active;
    private Set<GrantedAuthority> authorities;

    public MyUserDetails(Usuario usuario) {
        this.nombreUsuario = usuario.getNombreUsuario();
        this.password = usuario.getPassword();
        this.active = true; // Supongamos que todos los usuarios están activos
        this.authorities = new HashSet<>();
        for (Roles role : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
