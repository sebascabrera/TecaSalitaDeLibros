package com.salitadelibros.salita.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Libro {
@Id

    private Long id;
@Column(name = "Titulo",nullable = false)
    private String titulo;

    private String autor;

    private String ilustrador;

    private String editorial;
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ElementCollection
    private List<String> categorias;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIlustrador() {
        return ilustrador;
    }

    public void setIlustrador(String ilustrador) {
        this.ilustrador = ilustrador;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
// Constructores

    public Libro() {
    }

    public Libro(String titulo, String autor, String ilustrador, String editorial, Genero genero, List<String> categorias) {
        this.titulo = titulo;
        this.autor = autor;
        this.ilustrador = ilustrador;
        this.editorial = editorial;
        this.genero = genero;
        this.categorias = categorias;
    }

    public void addLibro(String titulo, String autor, String ilustrador, String editorial, Genero genero, List<String> categorias) {
        this.titulo = titulo;
        this.autor = autor;
        this.ilustrador = ilustrador;
        this.editorial = editorial;
        this.genero = genero;
        this.categorias = categorias;
    }

}
