package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.LibroCategoria;
import com.salitadelibros.salita.repositories.LibroCategoriaRepositorio;
import com.salitadelibros.salita.services.services.LibroCategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroCategoriaServicioImpl implements LibroCategoriaServicio {
@Autowired
    LibroCategoriaRepositorio libroCategoriaRepositorio;
    public void saveOrUpdate(LibroCategoria libroCategoria){

     libroCategoriaRepositorio.save(libroCategoria);
    }
}
