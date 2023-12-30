package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class LibroIlustrador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ilustrador ilustrador;

    @ManyToOne(fetch = FetchType.EAGER)
    private Libro libro;

    // Constructores

    public LibroIlustrador() {
    }

    public LibroIlustrador(Libro libro, Ilustrador ilustrador) {
        this.libro = libro;
        this.ilustrador = ilustrador;
    }

    // getters


    public Long getId() {
        return id;
    }

    public Ilustrador getIlustrador() {
        return ilustrador;
    }

    public Libro getLibro() {
        return libro;
    }

    // setters


    public void setIlustrador(Ilustrador ilustrador) {
        this.ilustrador = ilustrador;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
