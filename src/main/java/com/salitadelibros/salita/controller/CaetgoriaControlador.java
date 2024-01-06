package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Categoria;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.models.LibroCategoria;
import com.salitadelibros.salita.repositories.CategoriaRepositorio;
import com.salitadelibros.salita.services.CategoriaServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CaetgoriaControlador {
    @Autowired
    ServicioComun servicioComun;
    @Autowired
    CategoriaServicio categoriaServicio;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @GetMapping("/categorias")
    public List<Categoria> getCategorias() {
        return categoriaServicio.getCategorias();
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdateCategoria(@RequestBody Categoria nuevaCategoria) {
        if (nuevaCategoria.getId() == null) {
            // Es una nueva categoría que aún no se ha guardado en la base de datos
            Categoria existingCategoria = categoriaServicio.findByPalabraCategoria(nuevaCategoria.getPalabraCategoria());

            if (existingCategoria == null) {
                // No hay una categoría con la misma palabra, así que la guardamos
                servicioComun.saveOrUpdateCategoria(nuevaCategoria);
                return ResponseEntity.ok("Categoria guardada o actualizada exitosamente");
            } else {
                // Ya existe una categoría con la misma palabra, puedes manejar esto según tus necesidades
                return ResponseEntity.ok("Ya existe una categoría con la misma palabra: " + nuevaCategoria.getPalabraCategoria());
            }
        }
        return null;
    }

}

