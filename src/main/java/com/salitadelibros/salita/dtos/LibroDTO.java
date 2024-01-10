package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class LibroDTO {

    private Long id;
    private String titulo;
    private Genero genero;
    private Set<LibroCategoriaDTO> categorias;
    private EditorialDTO editorial;
    private Set<LibroAutorDTO> autores;
    private Set<LibroIlustradorDTO> ilustradores;
    private String fechaDeEdicion;
    private String isbn;

    // Constructores

    public LibroDTO() {
    }

    public LibroDTO(Libro libro) {
        id = libro.getId();
        titulo = libro.getTitulo();
        genero = libro.getGenero();
        categorias = libro.getCategorias()
                .stream()
                .map(libroCategoria -> new LibroCategoriaDTO(libroCategoria))
                .collect(Collectors.toSet());
        editorial = new EditorialDTO(libro.getEditorial());
        autores = libro.getAutores()
                .stream()
                .map(libroAutor -> new LibroAutorDTO(libroAutor))
                .collect(Collectors.toSet());
        ilustradores = libro.getIlustradores()
                .stream()
                .map(libroIlustrador -> new LibroIlustradorDTO(libroIlustrador))
                .collect(Collectors.toSet());
        fechaDeEdicion = libro.getFechaDeEdicion();
        isbn = libro.getIsbn();
    }


    // Getters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public Set<LibroCategoriaDTO> getCategorias() {
        return categorias;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public Set<LibroAutorDTO> getAutores() {
        return autores;
    }

    public Set<LibroIlustradorDTO> getIlustradores() {
        return ilustradores;
    }

    public String getFechaDeEdicion() {
        return fechaDeEdicion;
    }

    public String getIsbn() {
        return isbn;
    }

}
