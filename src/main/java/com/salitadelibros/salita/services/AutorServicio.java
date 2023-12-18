package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorServicio {

    List<Autor> getAutores();

    Optional<Autor> getAutor(Long id);

    void saveOrUpdate(Autor autor);

    void delete(Long id);
}
