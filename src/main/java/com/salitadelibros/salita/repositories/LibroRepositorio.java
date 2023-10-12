package com.salitadelibros.salita.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LibroRepositorio {
    List<Long>findById(Long id);
}
