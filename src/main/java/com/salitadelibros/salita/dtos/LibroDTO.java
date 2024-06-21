package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.*;

import java.util.Set;
import java.util.stream.Collectors;


public class LibroDTO {

    private Long id;
    private String titulo;
    private Genero genero;
    private Set<CategoriaDTO> categorias;
    private EditorialDTO editorial;
    private Set<AutorDTO> autores;
    private Set<IlustradorDTO> ilustradores;
    private String fechaDeEdicion;
    private String isbn;

    private String comentario;

    // Constructores

    public LibroDTO() {
    }

    public LibroDTO(Libro libro) {
        id = libro.getId();
        titulo = libro.getTitulo();
        genero = libro.getGenero();
        categorias = libro.getCategorias()
                .stream()
                .map(categorias -> new CategoriaDTO(categorias
                        .getCategoria()))
                .collect(Collectors.toSet());
        editorial = new EditorialDTO(libro.getEditorial());
        autores = libro.getAutores()
                .stream()
                .map(autores -> new AutorDTO(autores.getAutor()))
                .collect(Collectors.toSet());
        ilustradores = libro.getIlustradores()
                .stream()
                .map(ilustradores -> new IlustradorDTO(ilustradores.getIlustrador()))
                .collect(Collectors.toSet());
        fechaDeEdicion = libro.getFechaDeEdicion();
        isbn = libro.getIsbn();
        comentario = libro.getComentario();
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

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public Set<AutorDTO> getAutores() {
        return autores;
    }

    public Set<IlustradorDTO> getIlustradores() {
        return ilustradores;
    }

    public String getFechaDeEdicion() {
        return fechaDeEdicion;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getComentario() { return comentario; }

}
