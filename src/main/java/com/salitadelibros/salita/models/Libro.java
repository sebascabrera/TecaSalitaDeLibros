package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "Titulo", nullable = false)
    private String titulo;
    @Column(name = "fecha_de_edicion")
    private LocalDate fechaDeEdicion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroCategoria> categorias = new HashSet<>();

    private String isbn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroIlustrador> ilustradores = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroAutor> autores = new HashSet<>();

    // Constructores

    public Libro() {
    }

    public Libro(String titulo, LocalDate fechaDeEdicion, Genero genero, String isbn) {
        this.titulo = titulo;
        this.fechaDeEdicion = fechaDeEdicion;
        this.genero = genero;
        this.isbn= isbn;
    }

    // getters
    // id tiene solo get
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Set<LibroAutor> getAutores() {
        return autores;
    }

    public Set<LibroIlustrador> getIlustradores() {
        return ilustradores;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public Set<LibroCategoria> getCategorias() {
        return categorias;
    }

    public LocalDate getFechaDeEdicion() {
        return fechaDeEdicion;
    }

    public String getIsbn() {
        return isbn;
    }

    //y setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setFechaDeEdicion(LocalDate fechaDeEdicion) {
        this.fechaDeEdicion = fechaDeEdicion;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    //metodos de add

    public void addLibroAutor(LibroAutor libroAutor) {
        libroAutor.setLibro(this);
        autores.add(libroAutor);
    }


    public void addLibroIlustrador(LibroIlustrador libroIlustrador){
        libroIlustrador.setLibro(this);
        ilustradores.add(libroIlustrador);
    }
    public void addLibroCategoria(LibroCategoria libroCategoria){
        libroCategoria.setLibro(this);
        categorias.add(libroCategoria);
    }

    public void addEditorial(Editorial editorial){
        this.editorial = editorial;
        editorial.getLibros().add(this);
    }
}
