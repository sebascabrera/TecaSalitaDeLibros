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
    @Column(name = "Titulo", nullable = false)
    private String titulo;
@OneToMany(fetch = FetchType.EAGER, mappedBy = "libro")
    private Set<LibroAutor> librosAutores = new HashSet<>();

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

    public Libro(String titulo, String ilustrador, Editorial editorial, Genero genero, List<String> categorias) {
        this.titulo = titulo;

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

    public Set<LibroAutor> getLibrosAutores() {
        return librosAutores;
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



    //metodo de add autorLibro


    public void addLibroAutor(LibroAutor libroAutor) {
        libroAutor.setLibro(this);
        librosAutores.add(libroAutor);
    }
}
