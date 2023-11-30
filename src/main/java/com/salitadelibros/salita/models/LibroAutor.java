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

    public LibroAutor(Libro libro, Autor autor) {
        this.libro = libro;
        this.autor = autor;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Autor getAutor() {
        return autor;
    }

    // Setters

    public void setLibro(Libro libro) {
        if (libro != null) {
            this.libro = libro;
        }
    }

    public void setAutor(Autor autor) {
        if (autor != null) {
            this.autor = autor;
        }
    }
}
