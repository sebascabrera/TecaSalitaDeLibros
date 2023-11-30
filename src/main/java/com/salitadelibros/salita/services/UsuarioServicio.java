package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Usuario;
import com.salitadelibros.salita.repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    public void saveOrUpdate(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }
}
