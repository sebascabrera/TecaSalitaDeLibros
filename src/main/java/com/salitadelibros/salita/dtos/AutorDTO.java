package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Autor;

public class AutorDTO {
    private Long id;
    private String nombreAutor;
    private String apellidoAutor;

    public AutorDTO() {
        // Constructor vacío necesario para la deserialización (si es necesario)
    }

    // Método constructor que acepta una instancia de Autor
    public AutorDTO(Autor autor) {
        this.id = autor.getId();
        this.nombreAutor = autor.getNombreAutor();
        this.apellidoAutor = autor.getApellidoAutor();
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
}
