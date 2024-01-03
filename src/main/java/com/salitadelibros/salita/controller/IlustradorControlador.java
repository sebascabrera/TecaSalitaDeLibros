package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.services.IlustradorServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ilustradores")
public class IlustradorControlador {

    @Autowired
    private IlustradorServicio ilustradorServicio;
    @Autowired
    private ServicioComun servicioComun;

    @GetMapping("/ilustradores")
    public List<Ilustrador> getIlustradores() {
        return ilustradorServicio.getIlustradores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ilustrador> getIlustrador(@PathVariable Long id) {
        return ResponseEntity.of(ilustradorServicio.getIlustrador(id));
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdate(@RequestBody Ilustrador nuevoIlustrador) {
        try {
            servicioComun.saveOrUpdateIlustrador(nuevoIlustrador);
            return new ResponseEntity<>("Ilustrador guardado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ilustradorServicio.delete(id);
        return ResponseEntity.ok("Ilustrador eliminado exitosamente");
    }
}

