package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.AutorDTO;
import com.salitadelibros.salita.models.Autor;
import com.salitadelibros.salita.services.services.AutorServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/autores")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    ServicioComun servicioComun;


    @GetMapping("/autores")
    public Set<AutorDTO> getAutores() {

       List<Autor> listaDeAutores = autorServicio.getAutores();
        Set<AutorDTO> listaDeAutoresDTO = listaDeAutores
                .stream()
                .map(autor -> new AutorDTO(autor))
                .collect(Collectors.toSet());

        return listaDeAutoresDTO;
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

            return new ResponseEntity<>("Autor guardado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el autor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        autorServicio.delete(id);
        return ResponseEntity.ok("Autor eliminado exitosamente");
    }
}

