package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Genero;
import com.salitadelibros.salita.models.Libro;

import javax.persistence.*;
import java.util.List;
// no realizo setter, eso lo  hago desde las entidades
public class LibroDTO {
    private Long id;

    private String titulo;

    private String autor;

    private String ilustrador;

    private Editorial editorial;

    private Genero genero;

    private List<String> categorias;

    public LibroDTO(Libro libro){
        id = libro.getId();
        titulo = libro.getTitulo();
        autor = libro.getAutor();
        ilustrador = libro.getIlustrador();
        editorial = libro.getEditorial(); // hacer stream de EditorialDTO
        genero = libro.getGenero();
        categorias = libro.getCategorias();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIlustrador() {
        return ilustrador;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public List<String> getCategorias() {
        return categorias;
    }
}
