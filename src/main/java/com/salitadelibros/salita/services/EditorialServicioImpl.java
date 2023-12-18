package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EditorialServicioImpl implements EditorialServicio{

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Override
    public void saveOrUpdate(Editorial editorial) {
        editorialRepositorio.save(editorial);
    }

    @Override
    public List<Editorial> getEditoriales() {
        return editorialRepositorio.findAll();
    }
    @Override
     public Optional<Editorial> getEditorial(Long id){ return editorialRepositorio.findById(id);}
}
