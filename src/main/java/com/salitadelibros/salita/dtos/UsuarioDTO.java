package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Roles;
import com.salitadelibros.salita.models.Usuario;

import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nombreUsuario;
    private String email;
    private Set<Roles> roles;

    private UsuarioDTO(){

    }
    private UsuarioDTO(Usuario usuario){
        id = usuario.getId();
        nombreUsuario = usuario.getNombreUsuario();
        email = usuario.getEmail();
        roles = usuario.getRoles();
    }
    public Long getId() {
        return id;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public String getEmail() {
        return email;
    }

    public Set<Roles> getRoles() {
        return roles;
    }
}
