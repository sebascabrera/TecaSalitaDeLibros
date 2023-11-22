package com.salitadelibros.salita.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @Column(name = "Titulo", nullable = false)
    private String titulo;

    private String autor;

    private String ilustrador;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ElementCollection
    private List<String> categorias;

    // Constructores

    public Libro() {
    }

    public Libro(String titulo, String autor, String ilustrador, Editorial editorial, Genero genero, List<String> categorias) {
        this.titulo = titulo;
        this.autor = autor;
        this.ilustrador = ilustrador;
        this.editorial = editorial;
        this.genero = genero;
        this.categorias = categorias;
    }

// getters
    // id tiene solo get
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIlustrador() {
        return ilustrador;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    //y setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIlustrador(String ilustrador) {
        this.ilustrador = ilustrador;
    }

   public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

}
