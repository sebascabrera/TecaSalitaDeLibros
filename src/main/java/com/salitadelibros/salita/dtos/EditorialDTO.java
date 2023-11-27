package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Editorial;

import java.util.Set;
import java.util.stream.Collectors;

public class EditorialDTO {
    private Long id;

    private String nombre;

    private Set<LibroDTO> libros;

    public EditorialDTO(Editorial editorial) {
        id = editorial.getId();
        nombre = editorial.getNombre();
        libros = editorial.getLibros().stream().map(libro -> new LibroDTO(libro)).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<LibroDTO> getLibros() {
        return libros;
    }
}
