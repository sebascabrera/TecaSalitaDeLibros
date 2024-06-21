package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroCategoria;

public class LibroCategoriaDTO {
    private Long id;
    private LibroDTO libro;
    private CategoriaDTO categoria;

    //Constructores
    public LibroCategoriaDTO() {
    }
    public LibroCategoriaDTO(LibroCategoria libroCategoria) {
        id = libroCategoria.getId();
        libro = new LibroDTO(libroCategoria.getLibro());
        categoria = new CategoriaDTO(libroCategoria.getCategoria());
    }
    //getters


    public Long getId() {
        return id;
    }

    public LibroDTO getLibroDTO() {
        return libro;
    }

    public CategoriaDTO getCategoriaDTO() {
        return categoria;
    }
}
