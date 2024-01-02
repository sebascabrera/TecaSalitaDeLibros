package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaServicio {

    List<Categoria> getCategorias();

    Optional<Categoria> getCategoria(Long id);

    void saveOrUpdate(Categoria categoria);

    void delete(Long id);
}
