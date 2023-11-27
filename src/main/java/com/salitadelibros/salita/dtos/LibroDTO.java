package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.Genero;
import com.salitadelibros.salita.models.Libro;

import java.util.List;
import java.util.stream.Collectors;

public class LibroDTO {
    private Long id;
    private String titulo;
    private String autor;  // Cambiado para ser más significativo
    private String ilustrador;
    private String editorialNombre;  // Cambiado para extraer solo el nombre
    private String generoNombre;  // Cambiado para extraer solo el nombre
    private List<String> categorias;

    public LibroDTO(Libro libro) {
        id = libro.getId();
        titulo = libro.getTitulo();
        autor = obtenerAutores(libro);
        ilustrador = libro.getIlustrador();
        editorialNombre = obtenerNombreEditorial(libro);
        generoNombre = obtenerNombreGenero(libro);
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

    public String getEditorialNombre() {
        return editorialNombre;
    }

    public String getGeneroNombre() {
        return generoNombre;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    // Método privado para obtener la representación de cadena de los autores
    private String obtenerAutores(Libro libro) {
        return libro.getLibrosAutores()
                .stream()
                .map(libroAutor -> {
                    Autor autor = libroAutor.getAutor();
                    return autor.getNombreAutor() + " " + autor.getApellidoAutor();
                })  // Suponiendo que 'Autor' tiene un campo 'nombre'
                .collect(Collectors.joining(", "));
    }

    // Método privado para obtener el nombre de la editorial
    private String obtenerNombreEditorial(Libro libro) {
        return (libro.getEditorial() != null) ? libro.getEditorial().getNombre() : null;
    }

    // Método privado para obtener el nombre del género
    private String obtenerNombreGenero(Libro libro) {
        return (libro.getGenero() != null) ? libro.getGenero().name() : null;
    }
}
