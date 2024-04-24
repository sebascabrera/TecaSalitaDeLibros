package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.repositories.EditorialRepositorio;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import com.salitadelibros.salita.services.services.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EditorialServicioImpl implements EditorialServicio {

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
     public Editorial getEditorialById(Long id){
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(id);
        if (optionalEditorial.isPresent()){
            Editorial editorial = optionalEditorial.get();
            return editorial;
        }
        return null;}
}
