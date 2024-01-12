package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class LibroCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Libro libro;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;


    //Constructores
    public LibroCategoria() {
    }
    public LibroCategoria(Libro libro, Categoria categoria) {
        this.libro = libro;
        this.categoria= categoria;
    }
    //getters


    public Long getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    //setters
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
