package com.salitadelibros.salita.services.implementations;

import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.repositories.IlustradorRepositorio;
import com.salitadelibros.salita.services.services.IlustradorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IlustradorServicioImpl implements IlustradorServicio {
    @Autowired
    private IlustradorRepositorio ilustradorRepositorio;

    @Override
    public List<Ilustrador> getIlustradores() {
        return ilustradorRepositorio.findAll();
    }

    @Override
    public Optional<Ilustrador> getIlustrador(Long id) {
        return ilustradorRepositorio.findById(id);
    }

    @Override
    public void saveOrUpdate(Ilustrador ilustrador) {
        ilustradorRepositorio.save(ilustrador);
    }

    @Override
    public void delete(Long id) {
        ilustradorRepositorio.deleteById(id);
    }

    @Override
    public Ilustrador findByNombreIlustradorAndApellidoIlustrador(String nombreIlustrador, String apellidoIlustrador) {
        return ilustradorRepositorio.findByNombreIlustradorAndApellidoIlustrador(nombreIlustrador, apellidoIlustrador);
    }

    @Override
    public Ilustrador getIlustradorById(Long id) {
        Optional<Ilustrador> optionalIlustrador = ilustradorRepositorio.findById(id);
        if (optionalIlustrador.isPresent()) {
            Ilustrador ilustrador = optionalIlustrador.get();
            return ilustrador;
        }
        return null;
    }
}
