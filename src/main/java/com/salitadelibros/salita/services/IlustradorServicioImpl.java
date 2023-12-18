package com.salitadelibros.salita.services;

import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.repositories.IlustradorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IlustradorServicioImpl implements IlustradorServicio{
    @Autowired
    private IlustradorRepositorio ilustradorRepositorio;

    public List<Ilustrador> getIlustradores() {
        return ilustradorRepositorio.findAll();
    }

    public Optional<Ilustrador> getIlustrador(Long id) {
        return ilustradorRepositorio.findById(id);
    }

    public void saveOrUpdate(Ilustrador ilustrador) {
        ilustradorRepositorio.save(ilustrador);
    }

    public void delete(Long id) {
        ilustradorRepositorio.deleteById(id);
    }
}
