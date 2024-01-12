package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroIlustrador;

public class LibroIlustradorDTO {
    private Long id;
    private IlustradorDTO ilustradorDTO;
    private LibroDTO libroDTO;

    //constructores

    public LibroIlustradorDTO() {
    }

    public LibroIlustradorDTO(LibroIlustrador libroIlustrador) {
        id = libroIlustrador.getId();
        ilustradorDTO = new IlustradorDTO(libroIlustrador.getIlustrador());
        libroDTO = new LibroDTO(libroIlustrador.getLibro());
    }

  // getters

    public Long getId() {
        return id;
    }

    public IlustradorDTO getIlustradorDTO() {
        return ilustradorDTO;
    }

    public LibroDTO getLibroDTO() {
        return libroDTO;
    }
}
