package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroAutor;


public class LibroAutorDTO {
    private Long id;
    private LibroDTO libro;
    private AutorDTO autor;

    //constructores
    public LibroAutorDTO(){

    }

    public LibroAutorDTO(LibroAutor libroAutor) {
        id = libroAutor.getId();
        libro = new LibroDTO(libroAutor.getLibro());
        autor = new AutorDTO(libroAutor.getAutor());
    }

    public Long getId() {
        return id;
    }

    public LibroDTO getLibro() {
        return libro;
    }

    public AutorDTO getAutor() {
        return autor;
    }
}

