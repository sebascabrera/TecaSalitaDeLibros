package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.LibroAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LibroAutorRepositorio extends JpaRepository<LibroAutor, Long> {
}
