package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.LibroIlustrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LibroIlustradorRepositorio extends JpaRepository<LibroIlustrador, Long> {
}
