package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Autor;

import java.util.Set;
import java.util.stream.Collectors;

public class AutorDTO {
    private Long id;
    private String nombreAutor;
    private String apellidoAutor;
    private Set<LibroAutorDTO> libros;

    public AutorDTO() {

    }

    // Método constructor que acepta una instancia de Autor
    public AutorDTO(Autor autor) {
        id = autor.getId();
        nombreAutor = autor.getNombreAutor();
        apellidoAutor = autor.getApellidoAutor();
        libros = autor.getLibros()
                .stream()
                .map(libroAutor -> new LibroAutorDTO(libroAutor))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public String getApellidoAutor() {
        return apellidoAutor;
    }

    public Set<LibroAutorDTO> getLibros() {
        return libros;
    }
}
