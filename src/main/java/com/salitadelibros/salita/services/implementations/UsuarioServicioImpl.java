package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import com.salitadelibros.salita.services.services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void saveOrUpdate(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public boolean existsByNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.existsByNombreUsuario(nombreUsuario);
    }
}
