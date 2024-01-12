package com.salitadelibros.salita.dtos;

import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.models.LibroIlustrador;

import java.util.Set;
import java.util.stream.Collectors;

public class IlustradorDTO {
    private Long id;
    private String nombreIlustrador;

    private String apellidoIlustrador;
    private Set<LibroIlustradorDTO> libros;

    // Constructores
    public IlustradorDTO() {
    }

    public IlustradorDTO(Ilustrador ilustrador) {
        id = ilustrador.getId();
        nombreIlustrador = ilustrador.getNombreIlustrador();
        apellidoIlustrador = ilustrador.getApellidoIlustrador();
        libros = ilustrador.getLibros()
                .stream()
                .map(libroIlustrador -> new LibroIlustradorDTO(libroIlustrador)).collect(Collectors.toSet());
    }

    // getters

    public long getId() {
        return id;
    }

    public String getNombreIlustrador() {
        return nombreIlustrador;
    }

    public String getApellidoIlustrador() {
        return apellidoIlustrador;
    }

    public Set<LibroIlustradorDTO> getLibros() {
        return libros;
    }
}
