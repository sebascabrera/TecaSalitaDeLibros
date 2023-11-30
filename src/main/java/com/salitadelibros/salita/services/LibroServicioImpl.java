package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroServicioImpl extends LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio; // Asumiendo que tienes un repositorio para la entidad Libro

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
    public List<String> getCategorias() {
        List<Libro> libros = libroRepositorio.findAll();
        return libros.stream()
                .flatMap(libro -> libro.getCategorias().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}

