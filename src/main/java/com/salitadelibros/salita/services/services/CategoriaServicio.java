package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaServicio {

    List<Categoria> getCategorias();

    Optional<Categoria> getCategoria(Long id);

    void saveOrUpdate(Categoria categoria);

    void delete(Long id);

    public Categoria findByPalabraCategoria(String palabraCategoria);

    public Categoria findCategoriaById(Long id);
}
