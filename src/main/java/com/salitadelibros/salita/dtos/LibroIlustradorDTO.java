package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroIlustrador;

public class LibroIlustradorDTO {
    private Long id;
    private IlustradorDTO ilustrador;
    private LibroDTO libro;

    //constructores

    public LibroIlustradorDTO() {
    }

    public LibroIlustradorDTO(LibroIlustrador libroIlustrador) {
        id = libroIlustrador.getId();
        libro = new LibroDTO(libroIlustrador.getLibro());
        ilustrador = new IlustradorDTO(libroIlustrador.getIlustrador());
    }

  // getters

    public Long getId() {
        return id;
    }

    public IlustradorDTO getIlustrador() {
        return ilustrador;
    }

    public LibroDTO getLibro() {
        return libro;
    }
}
