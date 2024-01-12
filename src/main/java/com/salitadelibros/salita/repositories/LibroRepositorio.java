package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

}
