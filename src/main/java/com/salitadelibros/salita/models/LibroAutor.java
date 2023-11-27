package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
public class LibroAutor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

@ManyToOne(fetch = FetchType.EAGER)
    private Libro libro;

    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    // Constructor
    public LibroAutor() {

    }

    // getters

    public Long getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Autor getAutor() {
        return autor;
    }


    //setters


    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
