package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UsuarioResitorio extends JpaRepository<Usuario, Long> {
}
