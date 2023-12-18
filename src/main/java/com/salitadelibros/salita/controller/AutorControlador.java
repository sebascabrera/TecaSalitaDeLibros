package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.services.AutorServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    ServicioComun servicioComun;


    @GetMapping("/autores")
    public List<Autor> getAutores() {
        return autorServicio.getAutores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Long id) {
        return autorServicio.getAutor(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> saveOrUpdate(@RequestBody Autor autor) {
        servicioComun.saveOrUpdateAutor(autor);
        return ResponseEntity.ok("Autor guardado o actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        autorServicio.delete(id);
        return ResponseEntity.ok("Autor eliminado exitosamente");
    }
}

