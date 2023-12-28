package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Ilustrador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String nombreIlustrador;
    private String apellidoIlustrador;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ilustrador", cascade = CascadeType.ALL)
    private Set<LibroIlustrador> libros = new HashSet<>();

    // constructores

    public Ilustrador() {
    }

    public Ilustrador(String nombreIlustrador, String apellidoIlustrador) {
        this.nombreIlustrador = nombreIlustrador;
        this.apellidoIlustrador = apellidoIlustrador;
    }

    //getters
    public Long getId() { return id; }
    public String getNombreIlustrador() {
        return nombreIlustrador;
    }

    public String getApellidoIlustrador() {
        return apellidoIlustrador;
    }

    public Set<LibroIlustrador> getLibros() {
        return libros;
    }


    //setters

    public void setNombreIlustrador(String nombreIlustrador) {
        this.nombreIlustrador = nombreIlustrador;
    }

    public void setApellidoIlustrador(String apellidoIlustrador) {
        this.apellidoIlustrador = apellidoIlustrador;
    }


    //metodos

    public void addLibroIlustrador (LibroIlustrador libroIlustrador) {
        libroIlustrador.setIlustrador(this);
        libros.add(libroIlustrador);
    }


}
