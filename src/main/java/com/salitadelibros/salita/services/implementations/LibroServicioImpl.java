package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import com.salitadelibros.salita.services.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class LibroServicioImpl implements LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Override
    public List<Libro> getLibros() {
        return libroRepositorio.findAll();
    }

    @Override
    public Optional<Libro> getLibro(Long libroId) {
        return libroRepositorio.findById(libroId);
    }

    @Override
    public void saveOrUpdate(Libro libro) {
        libroRepositorio.save(libro);
    }

    @Override
    public void delete(Long libroId) {
        libroRepositorio.deleteById(libroId);
    }
    @Override
    public Libro getLibroById(Long id) {
        Optional<Libro> optionalLibro = libroRepositorio.findById(id);
        if (optionalLibro.isPresent()) {
            Libro libro = optionalLibro.get();
            return libro;
        }
        return null;
    }
}
