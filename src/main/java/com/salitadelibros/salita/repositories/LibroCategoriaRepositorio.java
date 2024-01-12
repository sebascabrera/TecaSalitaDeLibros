package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.LibroCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LibroCategoriaRepositorio  extends JpaRepository<LibroCategoria, Long> {
}
