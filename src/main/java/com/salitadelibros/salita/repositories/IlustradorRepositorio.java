package com.salitadelibros.salita.repositories;

import com.salitadelibros.salita.models.Ilustrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IlustradorRepositorio extends JpaRepository<Ilustrador, Long> {
    Ilustrador findByNombreIlustrador(String nombreIlustrador);

    Ilustrador findByApellidoIlustrador(String apellidoIlustrador);

    Ilustrador findByNombreIlustradorAndApellidoIlustrador(String nombreIlustrador, String apellidoIlustrador);
}
