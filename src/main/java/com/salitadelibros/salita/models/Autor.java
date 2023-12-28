package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String nombreAutor;
    private String apellidoAutor;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor", cascade = CascadeType.ALL)
    private Set<LibroAutor> libros = new HashSet<>();

    // Constructores

    public Autor() {

    }

    public Autor(String nombreAutor, String apellidoAutor) {
        this.nombreAutor = nombreAutor;
        this.apellidoAutor = apellidoAutor;

    }

    // Getters

    public long getId() {
        return id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public String getApellidoAutor() {
        return apellidoAutor;
    }

    public Set<LibroAutor> getLibros() {
        return libros;
    }

    // Setters

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }

    // Método addLibroAutor

    public void addLibroAutor(LibroAutor libroAutor ) {
        libroAutor.setAutor(this);
        libros.add(libroAutor);
    }
}
