package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping // Solo "/api/libros" ya que ya estás en esa ruta por la anotación de clase @RequestMapping
    public List<Libro> getAll() {
        return libroServicio.getLibro();
    }

    @GetMapping("/{libroId}")
    public Optional<Libro> getById(@PathVariable("libroId") Long libroId) {
        return libroServicio.getLibro(libroId);
    }

    @PostMapping("/libros")
    public void saveOrUpdate(@RequestBody Libro libro) {
        libroServicio.saveOrUpdate(libro);
    }

    @DeleteMapping("/{libroId}")
    public void delete(@PathVariable("libroId") Long libroId) {
        libroServicio.delete(libroId);
    }


}
