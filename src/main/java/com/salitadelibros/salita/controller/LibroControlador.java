package com.salitadelibros.salita.controller;

import com.salitadelibros.salita.dtos.LibroDTO;
import com.salitadelibros.salita.models.Libro;
import com.salitadelibros.salita.repositories.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class LibroControlador {

    @Autowired
    private LibroRepositorio libroRepositorio;

    // metodo que va a devolver una lista de libros que se la pido al repositorio por eso esta inyectada
    // /api/libros servlet una ruta de una petición asociado a un metodo
    @RequestMapping("/libros")
    public List<LibroDTO> getLibros() {
        List<Libro> listaLibros = libroRepositorio.findAll();
// por cada libro de listaLibros va a funcionar como parametro de LibroDTO
// el stream permite el uso de map y a la salida tengo un tipoStream de LibroDTO; con collect lo paso a lista de nuevo

        List<LibroDTO> libroDTOList = listaLibros
                .stream()
                .map(libro -> new LibroDTO(libro))
                .collect(Collectors.toList());
return libroDTOList;
    }

  /*
    @GetMapping("/categorias")
    public List<String> getCategorias() {
        return LibroRepositorio.getCategorias();
    }

    @PostMapping
    public void saveOrUpdate(@RequestBody Libro libro) {
        LibroRepositorio.saveOrUpdate(libro);
    }

    @DeleteMapping("/{libroId}")
    public void delete(@PathVariable("libroId") Long libroId) {
        LibroRepositorio.delete(libroId);
    }*/
}
