package com.salitadelibros.salita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Libro {
@Id

    private Long id;
@Column(name = "Titulo",nullable = false)
    private String titulo;

    private String autor;

    private String ilustrador;

    private String editorial;

    private Genero genero;

    public Libro() {
    }

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

    public void addLibro(String titulo, String autor, String ilustrador, String editorial, Genero genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.ilustrador = ilustrador;
        this.editorial = editorial;
        this.genero = genero;
    }
}
