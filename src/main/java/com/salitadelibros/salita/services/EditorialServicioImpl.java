package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

public class EditorialServicioImpl implements EditorialServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Override
    public void saveOrUpdate(Editorial editorial) {
        editorialRepositorio.save(editorial);
    }
}
