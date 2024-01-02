package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Libro;


import java.util.List;
import java.util.Optional;

public interface LibroServicio {
    List<Libro> getLibros();
    Optional<Libro> getLibro(Long id);
    void saveOrUpdate(Libro libro);
    void delete(Long id);

}
