package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Ilustrador;

import java.util.List;
import java.util.Optional;

public interface IlustradorServicio {
    List<Ilustrador> getIlustradores();

    Optional<Ilustrador> getIlustrador(Long id);

    void saveOrUpdate(Ilustrador ilustrador);

    void delete(Long id);

    Ilustrador findByNombreIlustradorAndApellidoIlustrador(String nombreIlustrador, String apellidoIlustrador);

    Ilustrador getIlustradorById(Long id);
}
