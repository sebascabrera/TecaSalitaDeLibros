package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.LibroCategoria;

public class LibroCategoriaDTO {
    private Long id;
    private LibroDTO libroDTO;
    private CategoriaDTO categoriaDTO;

    //Constructores
    public LibroCategoriaDTO() {
    }
    public LibroCategoriaDTO(LibroCategoria libroCategoria) {
        id = libroCategoria.getId();
        libroDTO = new LibroDTO(libroCategoria.getLibro());
        categoriaDTO = new CategoriaDTO(libroCategoria.getCategoria());
    }
    //getters


    public Long getId() {
        return id;
    }

    public LibroDTO getLibroDTO() {
        return libroDTO;
    }

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }
}
