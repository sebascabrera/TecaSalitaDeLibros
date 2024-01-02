package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Categoria;
import com.salitadelibros.salita.models.Editorial;
import com.salitadelibros.salita.repositories.CategoriaRepositorio;
import com.salitadelibros.salita.services.CategoriaServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CaetgoriaControlador {
    @Autowired
    ServicioComun servicioComun;
    @Autowired
    CategoriaServicio categoriaServicio;

    @GetMapping("/categorias")
    public List<Categoria> getCategorias() {
        return categoriaServicio.getCategorias();
    }

    @PostMapping("/guardarCategoria")
    public ResponseEntity<String> saveOrUpdateCategoria(@RequestBody Categoria categoria) {
        servicioComun.saveOrUpdateCategoria(categoria);
        return ResponseEntity.ok("Categoria guardada o actualizada exitosamente");
    }
}
