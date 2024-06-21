package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.CategoriaDTO;
import com.salitadelibros.salita.models.Categoria;
import com.salitadelibros.salita.repositories.CategoriaRepositorio;
import com.salitadelibros.salita.services.services.CategoriaServicio;
import com.salitadelibros.salita.services.ServicioComun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Set<CategoriaDTO> getCategorias() {

        List<Categoria>categoriaLista =  categoriaServicio.getCategorias();
        Set<CategoriaDTO> categoriaDTOLista = categoriaLista
                .stream()
                .map(categoria -> new CategoriaDTO(categoria))
                .collect(Collectors.toSet());

        return categoriaDTOLista;
    }

    @PostMapping
    public ResponseEntity<String> saveOrUpdateCategoria(@RequestBody Categoria nuevaCategoria) {
       try {
           if (nuevaCategoria.getId() == null) {
               // Es una nueva categoría que aún no se ha guardado en la base de datos
               Categoria existingCategoria = categoriaServicio.findByPalabraCategoria(nuevaCategoria.getPalabraCategoria());

               if (existingCategoria == null) {
                   // No hay una categoría con la misma palabra, así que la guardamos
                   servicioComun.saveOrUpdateCategoria(nuevaCategoria);

               }
           }
           return ResponseEntity.ok("Categoria guardada exitosamente");
       }catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}

