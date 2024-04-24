package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.repositories.AutorRepositorio;
import com.salitadelibros.salita.services.services.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServicioImpl implements AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Override
    public List<Autor> getAutores() {
        return autorRepositorio.findAll();
    }

    @Override
    public Optional<Autor> getAutor(Long id) {
        return autorRepositorio.findById(id);
    }

    @Override
    public void saveOrUpdate(Autor autor) {
        autorRepositorio.save(autor);
    }

    @Override
    public void delete(Long id) {
        autorRepositorio.deleteById(id);
    }
    @Override
    public Autor findByNombreAutor( String nombreAutor){
        return autorRepositorio.findByNombreAutor(nombreAutor);
    }
    @Override
    public Autor findByApellidoAutor(String apellidoAutor){
        return autorRepositorio.findByApellidoAutor(apellidoAutor);
    }
    @Override
    public Autor findByNombreAutorAndApellidoAutor(String nombreAutor, String apellidoAutor){
        return autorRepositorio.findByNombreAutorAndApellidoAutor(nombreAutor, apellidoAutor);
    }
    @Override
    public Autor getAutorById(Long id) {
        Optional<Autor> optionalAutor = autorRepositorio.findById(id);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            return autor;
        }
        return null;
    }
}
