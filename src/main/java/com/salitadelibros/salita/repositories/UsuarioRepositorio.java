package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Override
    List<Usuario> findAllById(Iterable<Long> longs);

    List<Usuario> findByMail(String mail);


}
