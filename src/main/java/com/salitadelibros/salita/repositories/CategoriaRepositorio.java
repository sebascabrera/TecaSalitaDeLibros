package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    Categoria findByPalabraCategoria(String palabraCategoria);
}
