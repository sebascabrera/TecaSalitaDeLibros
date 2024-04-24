package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Libro;

import java.util.Set;
import java.util.stream.Collectors;

public class EditorialDTO {
    private Long id;

    private String nombreEditorial;



    public EditorialDTO(){

    }

    public EditorialDTO(Editorial editorial) {
        id = editorial.getId();
        nombreEditorial = editorial.getNombreEditorial();

    }

    public Long getId() {
        return id;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

}
