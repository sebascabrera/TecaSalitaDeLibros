package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private int fechaDeEdicion;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroIlustrador> librosIlustradores = new HashSet<>();

    @Column(name = "Titulo", nullable = false)
    private String titulo;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "libro", cascade = CascadeType.ALL)
    private Set<LibroAutor> librosAutores = new HashSet<>();

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

    public Libro(String titulo, Editorial editorial, Genero genero, List<String> categorias, int fechaDeEdicion) {
        this.titulo = titulo;
        this.fechaDeEdicion = fechaDeEdicion;

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

    public Set<LibroAutor> getLibrosAutores() {
        return librosAutores;
    }

    public Set<LibroIlustrador> getLibrosIlustradores() {
        return librosIlustradores;
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

    public int getFechaDeEdicion() {
        return fechaDeEdicion;
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

    public void setFechaDeEdicion(int fechaDeEdicion) {
        this.fechaDeEdicion = fechaDeEdicion;
    }

    //metodo de add autorLibro


    public void addLibroAutor(LibroAutor libroAutor) {
        // Crea una nueva instancia de LibroAutor si no se proporciona
        if (libroAutor == null) {
            libroAutor = new LibroAutor();
        }

        libroAutor.setLibro(this);
        librosAutores.add(libroAutor);
    }


    public void addLibroIlustrador(LibroIlustrador libroIlustrador){
        libroIlustrador.setLibro(this);
        librosIlustradores.add(libroIlustrador);
    }

}
