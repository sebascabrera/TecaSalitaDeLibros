package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LibroDTO {
    private Long id;
    private String titulo;
    private String autor;  // Cambiado para ser más significativo
    private String ilustrador;
    private Editorial editorial;  // Cambiado para extraer solo el nombre
    private Genero genero;  // Cambiado para extraer solo el nombre
    private List<String> categorias;
    private LocalDate fechaDeEdicion;
    private String isbn;

    public LibroDTO(Libro libro) {
        id = libro.getId();
        titulo = libro.getTitulo();
        autor = obtenerAutores(libro);
        ilustrador = obtenerIlustrador(libro);
        editorial = obtenerNombreEditorial(libro);
        genero = obtenerNombreGenero(libro);
        categorias = libro.getCategorias();
        fechaDeEdicion = libro.getFechaDeEdicion();
        isbn = libro.getIsbn();
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

    public Editorial getEditorialNombre() {
        return editorial;
    }

    public Genero getGeneroNombre() {
        return genero;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public LocalDate getFechaDeEdicion() {
        return fechaDeEdicion;
    }

    public String getIsbn() {
        return isbn;
    }

    // Método privado para obtener la representación de cadena de los autores
    private String obtenerAutores(Libro libro) {
        return libro.getAutores()
                .stream()
                .map(libroAutor -> {
                    Autor autor = libroAutor.getAutor();
                    return autor.getNombreAutor() + " " + autor.getApellidoAutor();
                })
                .collect(Collectors.joining(", "));
    }

    // Método privado para obtener el nombre de la editorial
    private Editorial obtenerNombreEditorial(Libro libro) {
        return (libro.getEditorial() != null) ? libro.getEditorial().getNombre() : null;
    }

    // Método privado para obtener el nombre del género
    private Genero obtenerNombreGenero(Libro libro) {
        return (libro.getGenero() != null) ? libro.getGenero().name() : null;
    }

    private String obtenerIlustrador(Libro libro) {
        return (libro.getIlustradores() != null) ? libro.getIlustradores()
                .stream()
                .map(libroIlustrador -> {
                    Ilustrador ilustrador = libroIlustrador.getIlustrador();
                    return ilustrador.getNombreIlustrador() + " " + ilustrador.getApellidoIlustrador();
                })
                .collect(Collectors.joining(", ")) : "";
    }

    public Libro toEntity() {
        Libro libro = new Libro();
        return libro;
    }
}
