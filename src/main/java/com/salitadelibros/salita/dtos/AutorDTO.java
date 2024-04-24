package com.salitadelibros.salita.dtos;
import com.salitadelibros.salita.models.Autor;


public class AutorDTO {
    private Long id;
    private String nombreAutor;
    private String apellidoAutor;


    public AutorDTO() {

    }

    public AutorDTO(Autor autor) {
        id = autor.getId();
        nombreAutor = autor.getNombreAutor();
        apellidoAutor = autor.getApellidoAutor();
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
