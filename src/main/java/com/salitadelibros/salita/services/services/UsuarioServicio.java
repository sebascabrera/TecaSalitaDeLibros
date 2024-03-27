package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Usuario;

public interface UsuarioServicio {

    void saveOrUpdate(Usuario usuario);

    Usuario findByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);
}
