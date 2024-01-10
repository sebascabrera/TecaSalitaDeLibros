package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String palabraCategoria;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "categoria",cascade = CascadeType.ALL)
    private Set<LibroCategoria> libros = new HashSet<>();

    public Categoria() {
    }
    public Categoria(String palabraCategoria){
        this.palabraCategoria = palabraCategoria;
    }
    //getters

    public Long getId() {
        return id;
    }

    public String getPalabraCategoria() {
        return palabraCategoria;
    }

    public Set<LibroCategoria> getLibros() {
        return libros;
    }


    //metodo add
    public void addLibroCategoria(LibroCategoria libroCategoria){
        libroCategoria.setCategoria(this);
        libros.add(libroCategoria);
    }

}
