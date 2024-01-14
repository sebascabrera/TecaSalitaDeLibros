package com.salitadelibros.salita.dtos;
import com.salitadelibros.salita.models.Ilustrador;

public class IlustradorDTO {
    private Long id;
    private String nombreIlustrador;

    private String apellidoIlustrador;

    // Constructores
    public IlustradorDTO() {
    }

    public IlustradorDTO(Ilustrador ilustrador) {
        id = ilustrador.getId();
        nombreIlustrador = ilustrador.getNombreIlustrador();
        apellidoIlustrador = ilustrador.getApellidoIlustrador();
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




}
