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
    private Long id;

    private String nombreAutor;

    private String apellidoAutor;

@OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
private Set<LibroAutor> librosAutores = new HashSet<>();

    // Constructores

    public Autor() {
    }

    public Autor(String nombreAutor, String apellidoAutor) {
        this.nombreAutor = nombreAutor; this.apellidoAutor = apellidoAutor;
    }


//getters
    public Long getId() {
        return id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public String getApellidoAutor() {
        return apellidoAutor;
    }

    public Set<LibroAutor> getLibrosAutores() {
        return librosAutores;
    }

    //Setters

    public void setnombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }

    // metodo add

    public void addLibroAutor(LibroAutor libroAutor){
        libroAutor.setAutor(this);
        librosAutores.add(libroAutor);
    }

}
