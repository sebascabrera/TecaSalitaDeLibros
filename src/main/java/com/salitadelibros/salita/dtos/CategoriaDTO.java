package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Categoria;
import com.salitadelibros.salita.models.LibroCategoria;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoriaDTO {
    private Long id;
    private String palabraCategoria;


    //constructores

    public CategoriaDTO() {
    }
    public CategoriaDTO(Categoria categoria){
        id =  categoria.getId();
        palabraCategoria = categoria.getPalabraCategoria();

    }
    //getters
    public Long getId() {
        return id;
    }

    public String getPalabraCategoria() {
        return palabraCategoria;
    }

}
