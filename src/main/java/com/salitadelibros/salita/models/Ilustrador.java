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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ilustrador")
    private Set<LibroIlustrador> libroIlustradores = new HashSet<>();

    // constructores

    public Ilustrador() {
    }

    public Ilustrador(String nombreIlustrador, String apellidoIlustrador) {
        this.nombreIlustrador = nombreIlustrador;
        this.apellidoIlustrador = apellidoIlustrador;
    }

    //getters
    public long getId() { return id; }
    public String getNombreIlustrador() {
        return nombreIlustrador;
    }

    public String getApellidoIlustrador() {
        return apellidoIlustrador;
    }

    public Set<LibroIlustrador> getLibroIlustradores() {
        return libroIlustradores;
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
        libroIlustradores.add(libroIlustrador);
    }


}
