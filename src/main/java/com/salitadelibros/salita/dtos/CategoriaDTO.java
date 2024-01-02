package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Categoria;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoriaDTO {
    private Long id;
    private String palabraCategoria;
    private Set<LibroCategoriaDTO> libros;

    //constructores

    public CategoriaDTO() {
    }
    public CategoriaDTO(Categoria categoria){
        id =  categoria.getId();
        palabraCategoria = categoria.getPalabraCategoria();
        libros = categoria.getLibros()
                .stream()
                .map(libroCategoria -> new LibroCategoriaDTO(libroCategoria))
                .collect(Collectors.toSet());
    }
    //getters
    public Long getId() {
        return id;
    }

    public String getPalabraCategoria() {
        return palabraCategoria;
    }

    public Set<LibroCategoriaDTO> getLibros() {
        return libros;
    }
}
