package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.services.AutorServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<?> guardarNuevoAutor(@RequestBody Autor nuevoAutor) {
        try {
            if (nuevoAutor == null) {
                return new ResponseEntity<>("El autor proporcionado es nulo", HttpStatus.BAD_REQUEST);
            }
            Autor autorExtNombreApellido = autorServicio.findByNombreAutorAndApellidoAutor(nuevoAutor.getNombreAutor(), nuevoAutor.getApellidoAutor());


            if (autorExtNombreApellido != null) {
                return new ResponseEntity<>("Ya existe un autor con el mismo nombre y apellido", HttpStatus.BAD_REQUEST);
            }

            servicioComun.saveOrUpdateAutor(nuevoAutor);

            return new ResponseEntity<>("AutorDTO guardado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el autor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        autorServicio.delete(id);
        return ResponseEntity.ok("AutorDTO eliminado exitosamente");
    }
}

