package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Editorial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {
}
