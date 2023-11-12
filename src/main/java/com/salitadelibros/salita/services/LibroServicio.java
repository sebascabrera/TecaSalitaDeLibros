package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    LibroRepositorio libroRepositorio;
// Metodos para obtener(listar) guardar o modificar y eliminar --> estos metodos los llamo desde el controlador
    public List<Libro> getLibros(){
        return libroRepositorio.findAll();
    }
    public Optional<Libro> getLibros(Long id){
        return libroRepositorio.findById(id);
    }

    public void saveOrUpdate(Libro libro){
        libroRepositorio.save(libro);
    }
    public void delete(Long id){
        libroRepositorio.deleteById(id);
    }
}
