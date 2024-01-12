package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Editorial;

import java.util.Set;
import java.util.stream.Collectors;

public class EditorialDTO {
    private Long id;

    private String nombreEditorial;

    private Set<LibroDTO> libroDTOS;

    public EditorialDTO(){

    }

    public EditorialDTO(Editorial editorial) {
        id = editorial.getId();
        nombreEditorial = editorial.getNombreEditorial();
        libroDTOS = editorial.getLibros().stream().map(libro -> new LibroDTO(libro)).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public Set<LibroDTO> getLibros() {
        return libroDTOS;
    }
}
