package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Ilustrador;

import java.util.List;
import java.util.Optional;

public interface IlustradorServicio {
    List<Ilustrador> getIlustradores();

    Optional<Ilustrador> getIlustrador(Long id);

    void saveOrUpdate(Ilustrador ilustrador);

    void delete(Long id);
}
