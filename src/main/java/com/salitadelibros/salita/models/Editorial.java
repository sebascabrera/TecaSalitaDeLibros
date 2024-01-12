package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String nombreEditorial;

    @OneToMany(mappedBy = "editorial", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Libro> libros = new HashSet<>();

    //Constructores
    public Editorial() {
    }

    public Editorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;

    }

    // getters

    public Long getId() {
        return id;
    }

    public String getNombreEditorial() {
        return nombreEditorial;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

// setters

    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public void addLibro(Libro libro) {
        this.libros.add(libro); // agrego libro al Set<libro>
        libro.addEditorial(this) ;// agrego la editorial al libro

    }

}
