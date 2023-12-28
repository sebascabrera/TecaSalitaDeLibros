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

    @Column(name = "fecha_de_edicion")
    private LocalDate fechaDeEdicion;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroIlustrador> ilustradores = new HashSet<>();

    @Column(name = "Titulo", nullable = false)
    private String titulo;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroAutor> autores = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ElementCollection
    private List<String> categorias;

    private String isbn;


    // Constructores

    public Libro() {
    }

    public Libro(String titulo, Editorial editorial, Genero genero, List<String> categorias, LocalDate fechaDeEdicion, String isbn) {
        this.titulo = titulo;
        this.fechaDeEdicion = fechaDeEdicion;

        this.editorial = editorial;
        this.genero = genero;
        this.categorias = categorias;
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

    public List<String> getCategorias() {
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


    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
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

}
