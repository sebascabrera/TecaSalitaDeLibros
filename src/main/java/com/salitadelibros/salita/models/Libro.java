package com.salitadelibros.salita.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.MultiValueMap;

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
    private String fechaDeEdicion;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
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

    public Libro(String titulo, String fechaDeEdicion, Genero genero, String isbn) {
        this.titulo = titulo;
        this.fechaDeEdicion = fechaDeEdicion;
        this.genero = genero;
        this.isbn= isbn;
    }

    public Libro(MultiValueMap<String, String> libroData) {
        if (libroData != null) {
            this.titulo = libroData.getFirst("titulo");
            this.fechaDeEdicion = libroData.getFirst("fechaDeEdicion");
            this.genero = Genero.valueOf(libroData.getFirst("genero"));
            this.isbn = libroData.getFirst("isbn");
        }
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

    public String getFechaDeEdicion() {
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

    public void setFechaDeEdicion(String fechaDeEdicion) {
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

    public void addEditorial(Editorial editorial) {
        if (this.editorial == null || !this.editorial.equals(editorial)) {
            this.editorial = editorial;
            editorial.getLibros().add(this);
        }
    }

    public void addLibroCategoria(Categoria categoria1, Libro libro1) {
    }
}
