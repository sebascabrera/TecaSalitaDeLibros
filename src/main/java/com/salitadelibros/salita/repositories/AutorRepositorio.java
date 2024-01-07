package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AutorRepositorio extends JpaRepository<Autor, Long> {
Autor findByNombreAutor(String nombreAutor);
Autor findByApellidoAutor(String apellidoAutor);

Autor findByNombreAutorAndApellidoAutor(String nombreAutor, String apellidoAutor);
}
