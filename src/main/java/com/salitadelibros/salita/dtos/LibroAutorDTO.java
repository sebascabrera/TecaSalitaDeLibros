package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroAutor;

public class LibroAutorDTO {
    private Long id;
    private LibroDTO libroDTO;
    private AutorDTO autorDTO;

    //constructores
    public LibroAutorDTO(){

    }

    public LibroAutorDTO(LibroAutor libroAutor) {
        id = libroAutor.getId();
        libroDTO = new LibroDTO(libroAutor.getLibro());
        autorDTO = new AutorDTO(libroAutor.getAutor());
    }

    public Long getId() {
        return id;
    }

    public LibroDTO getLibroDTO() {
        return libroDTO;
    }

    public AutorDTO getAutorDTO() {
        return autorDTO;
    }
}

