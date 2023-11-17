package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping
    public List<Libro> getAllLibros() {
        return libroServicio.getLibro();
    }

    @GetMapping("/categorias")
    public List<String> getCategorias() {
        return libroServicio.getCategorias();
    }

    @PostMapping
    public void saveOrUpdate(@RequestBody Libro libro) {
        libroServicio.saveOrUpdate(libro);
    }

    @DeleteMapping("/{libroId}")
    public void delete(@PathVariable("libroId") Long libroId) {
        libroServicio.delete(libroId);
    }
}
