package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.services.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class LibroControlador {
@Autowired
   private LibroServicio libroServicio;

@GetMapping
    public List<Libro> getAll(){
    return libroServicio.getLibros();
    }

  //consultar
  @GetMapping("/{libroId}")
  public Optional<Libro> getById(@PathVariable("libroId")Long libroId){
    return libroServicio.getLibros(libroId);
  }


    @PostMapping ("/libros")
    public void saveOrUpdate(@RequestBody Libro libro){
     libroServicio.saveOrUpdate(libro);
    }

    @DeleteMapping("/{libroId}")
    public void delete(@PathVariable("libroId") Long libroId){
    libroServicio.delete(libroId);
    }
}
