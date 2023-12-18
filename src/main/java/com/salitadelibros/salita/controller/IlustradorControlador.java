package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Ilustrador;
import com.salitadelibros.salita.services.IlustradorServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Ilustrador> getIlustradores() {
        return ilustradorServicio.getIlustradores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ilustrador> getIlustrador(@PathVariable Long id) {
        return ResponseEntity.of(ilustradorServicio.getIlustrador(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> saveOrUpdate(@RequestBody Ilustrador ilustrador) {
        servicioComun.saveOrUpdateIlustrador(ilustrador);
        return ResponseEntity.ok("Ilustrador guardado o actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ilustradorServicio.delete(id);
        return ResponseEntity.ok("Ilustrador eliminado exitosamente");
    }
}

