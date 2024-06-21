package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.LibroIlustrador;
import com.salitadelibros.salita.repositories.LibroIlustradorRepositorio;
import com.salitadelibros.salita.services.services.LibroIlustradorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroIlustradorServicioImpl implements LibroIlustradorServicio {
    @Autowired
    LibroIlustradorRepositorio libroIlustradorRepositorio;
    public void saveOrUpdate(LibroIlustrador libroIlustrador){
        libroIlustradorRepositorio.save(libroIlustrador);
    }
}
