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
    public Ilustrador findByNombreIlustradorAndApellidoIlustrador(String nombreIlustrador, String apellidoIlustrador){
        return ilustradorRepositorio.findByNombreIlustradorAndApellidoIlustrador(nombreIlustrador, apellidoIlustrador);
    }
}
