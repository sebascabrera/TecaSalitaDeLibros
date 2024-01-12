package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.LibroAutor;
import com.salitadelibros.salita.repositories.LibroAutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroAutorServicioImpl implements LibroAutorServicio {
@Autowired
    LibroAutorRepositorio libroAutorRepositorio;

    @Override
  public void saveOrUpdate(LibroAutor libroAutor){
        libroAutorRepositorio.save(libroAutor);
    }
}
