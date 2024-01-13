package com.salitadelibros.salita.services.services;

import com.salitadelibros.salita.models.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorServicio {

    List<Autor> getAutores();

    Optional<Autor> getAutor(Long id);

    void saveOrUpdate(Autor autor);

    void delete(Long id);

    public Autor findByNombreAutor( String nombreAutor);

    public Autor findByApellidoAutor(String apellidoAutor);

    public Autor findByNombreAutorAndApellidoAutor(String nombreAutor, String apellidoAutor);

    public Autor getAutorById(Long id);
}
